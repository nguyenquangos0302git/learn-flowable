package com.example.demo01.service.impl;

import com.example.demo01.dto.StartApprovalRequest;
import com.example.demo01.service.IApprovalProcessService;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalProcessServiceImpl implements IApprovalProcessService {

    private final RuntimeService runtimeService;

    @Override
    public ProcessInstance start(StartApprovalRequest request) {

        List<Map<String, List<String>>> approvalLevels =
                request.getLevels().stream()
                        .map(level -> Map.of(
                                "signers", level.getSigners()
                        ))
                        .collect(Collectors.toList());

        Map<String, Object> vars = new HashMap<>();
        vars.put("documentId", request.getDocumentId());
        vars.put("approvalLevels", approvalLevels);

        return runtimeService.startProcessInstanceByKey(
                "multiLevelApprovalProcess",
                vars
        );
    }

}
