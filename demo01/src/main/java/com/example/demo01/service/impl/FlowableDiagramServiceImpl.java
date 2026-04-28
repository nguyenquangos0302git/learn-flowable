package com.example.demo01.service.impl;

import com.example.demo01.service.IFlowableDiagramService;
import lombok.AllArgsConstructor;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlowableDiagramServiceImpl implements IFlowableDiagramService {

    private final ProcessEngine processEngine;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;

    @Override
    public byte[] generateDiagram(String processInstanceId)
            throws IOException {

        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();

        String processDefinitionId =
                processInstance.getProcessDefinitionId();

        // ===== Active activities =====
        List<String> activeActivityIds =
                runtimeService.getActiveActivityIds(processInstanceId);

        // ===== Finished activities =====
        List<String> finishedActivityIds =
                historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .finished()
                        .list()
                        .stream()
                        .map(HistoricActivityInstance::getActivityId)
                        .toList();

        // ===== Highlighted flows =====
        List<String> highlightedFlows =
                calculateHighlightedFlows(processDefinitionId,
                        finishedActivityIds);

        List<String> highlightedActivities = new ArrayList<>();
        highlightedActivities.addAll(activeActivityIds);
        highlightedActivities.addAll(finishedActivityIds);

        BpmnModel bpmnModel =
                repositoryService.getBpmnModel(processDefinitionId);

        ProcessDiagramGenerator generator =
                processEngine.getProcessEngineConfiguration()
                        .getProcessDiagramGenerator();

        try (InputStream imageStream =
                     generator.generateDiagram(
                             bpmnModel,
                             "png",
                             highlightedActivities,   // ✅ chỉ 1 list
                             highlightedFlows,        // ✅ sequence flow
                             "Arial",
                             "Arial",
                             "Arial",
                             null,
                             1.0,
                             true
                     )) {

            return imageStream.readAllBytes();
        }
    }

    private List<String> calculateHighlightedFlows(
            String processDefinitionId,
            List<String> finishedActivities) {

        List<String> highlightedFlows = new ArrayList<>();

        BpmnModel model =
                repositoryService.getBpmnModel(processDefinitionId);

        org.flowable.bpmn.model.Process process =
                model.getMainProcess();

        for (FlowElement fe : process.getFlowElements()) {
            if (fe instanceof SequenceFlow flow) {
                if (finishedActivities.contains(flow.getSourceRef()) &&
                        finishedActivities.contains(flow.getTargetRef())) {
                    highlightedFlows.add(flow.getId());
                }
            }
        }
        return highlightedFlows;
    }

}
