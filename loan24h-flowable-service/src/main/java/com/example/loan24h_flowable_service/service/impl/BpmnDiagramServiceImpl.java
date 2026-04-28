package com.example.loan24h_flowable_service.service.impl;

import com.example.loan24h_flowable_service.payload.request.LoanProposalRequest;
import com.example.loan24h_flowable_service.payload.request.ProposalClaimRequest;
import com.example.loan24h_flowable_service.payload.response.BpmnDeployResponse;
import com.example.loan24h_flowable_service.payload.response.ProposalClaimResponse;
import com.example.loan24h_flowable_service.payload.response.StartWorkflowResponse;
import com.example.loan24h_flowable_service.service.IBpmnDiagramService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BpmnDiagramServiceImpl implements IBpmnDiagramService {

    private final ProcessEngine processEngine;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;

    @Override
    public BpmnDeployResponse deploy(MultipartFile file) throws IOException {

        Deployment deployment = repositoryService
                .createDeployment()
                .name(file.getOriginalFilename())
                .addInputStream(
                        file.getOriginalFilename(),
                        file.getInputStream()
                )
                .deploy();

        return new BpmnDeployResponse(deployment.getId());
    }

    @Override
    public StartWorkflowResponse start(LoanProposalRequest loanProposalRequest) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("duyet24h");
        return new StartWorkflowResponse(processInstance.getProcessInstanceId());
    }

    @Override
    public byte[] generateDiagram(String processInstanceId) throws IOException {

        String processDefinitionId;
        List<String> activeActivityIds = new ArrayList<>();
        List<String> finishedActivityIds;

        /*
         * Bước 1:
         * Kiểm tra process còn đang chạy hay đã kết thúc
         */
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();

        if (processInstance != null) {
            /*
             * PROCESS ĐANG CHẠY
             */
            processDefinitionId = processInstance.getProcessDefinitionId();

            activeActivityIds =
                    runtimeService.getActiveActivityIds(processInstanceId);

        } else {
            /*
             * PROCESS ĐÃ KẾT THÚC
             */
            HistoricProcessInstance historicProcessInstance =
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId)
                            .singleResult();

            if (historicProcessInstance == null) {
                throw new RuntimeException(
                        "Không tìm thấy processInstanceId: " + processInstanceId
                );
            }

            processDefinitionId =
                    historicProcessInstance.getProcessDefinitionId();
        }

        /*
         * Bước 2:
         * Lấy các activity đã hoàn thành từ history
         */
        finishedActivityIds =
                historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .finished()
                        .orderByHistoricActivityInstanceEndTime()
                        .asc()
                        .list()
                        .stream()
                        .map(HistoricActivityInstance::getActivityId)
                        .distinct()
                        .collect(Collectors.toList());

        /*
         * Bước 3:
         * Tính highlighted flows
         */
        List<String> highlightedFlows =
                calculateHighlightedFlows(
                        processInstanceId
                );

        /*
         * Bước 4:
         * Merge active + finished activities
         */
        List<String> highlightedActivities = new ArrayList<>();
        highlightedActivities.addAll(finishedActivityIds);
//        highlightedActivities.addAll(activeActivityIds);

        /*
         * Bước 5:
         * Generate BPMN diagram
         */
        BpmnModel bpmnModel =
                repositoryService.getBpmnModel(processDefinitionId);

        ProcessDiagramGenerator generator =
                processEngine
                        .getProcessEngineConfiguration()
                        .getProcessDiagramGenerator();

        try (InputStream imageStream =
                     generator.generateDiagram(
                             bpmnModel,
                             "png",
                             highlightedActivities,
                             highlightedFlows,
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


//    private List<String> calculateHighlightedFlows(
//            String processInstanceId,
//            String processDefinitionId) {
//
//        List<String> highlightedFlows = new ArrayList<>();
//
//        BpmnModel bpmnModel =
//                repositoryService.getBpmnModel(processDefinitionId);
//
//        List<HistoricActivityInstance> historicActivities =
//                historyService.createHistoricActivityInstanceQuery()
//                        .processInstanceId(processInstanceId)
//                        .orderByHistoricActivityInstanceStartTime()
//                        .asc()
//                        .list();
//
//        for (int i = 0; i < historicActivities.size() - 1; i++) {
//
//            HistoricActivityInstance current =
//                    historicActivities.get(i);
//
//            HistoricActivityInstance next =
//                    historicActivities.get(i + 1);
//
//            FlowElement flowElement =
//                    bpmnModel.getMainProcess()
//                            .getFlowElement(current.getActivityId());
//
//            /*
//             * Chỉ xử lý nếu là FlowNode
//             */
//            if (!(flowElement instanceof FlowNode currentNode)) {
//                continue;
//            }
//
//            for (SequenceFlow sequenceFlow :
//                    currentNode.getOutgoingFlows()) {
//
//                if (sequenceFlow.getTargetRef()
//                        .equals(next.getActivityId())) {
//
//                    highlightedFlows.add(sequenceFlow.getId());
//                    break;
//                }
//            }
//        }
//
//        return highlightedFlows;
//    }
private List<String> calculateHighlightedFlows(
        String processInstanceId) {

    return historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .finished()
            .list()
            .stream()
            .filter(h ->
                    "sequenceFlow".equals(h.getActivityType()))
            .map(HistoricActivityInstance::getActivityId)
            .distinct()
            .collect(Collectors.toList());
}
}
