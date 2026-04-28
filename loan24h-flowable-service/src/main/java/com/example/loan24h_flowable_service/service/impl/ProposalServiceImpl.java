package com.example.loan24h_flowable_service.service.impl;

import com.example.loan24h_flowable_service.payload.request.*;
import com.example.loan24h_flowable_service.payload.response.*;
import com.example.loan24h_flowable_service.service.IProposalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ProposalServiceImpl implements IProposalService {

    private final TaskService taskService;

    @Override
    public List<ProposalResponse> getUnassignedProposal() {
        List<Task> tasks = taskService
                .createTaskQuery()
                .taskCandidateGroupIn(
                        Arrays.asList("LM1")
                )
                .list();

        return tasks
                .stream()
                .map(task -> {
                    ProposalResponse proposalResponse = new ProposalResponse();
                    proposalResponse.setTaskId(task.getId());
                    proposalResponse.setProcessInstanceId(task.getProcessInstanceId());
                    return proposalResponse;
                })
                .toList();

    }

    @Override
    public ProposalClaimResponse claim(ProposalClaimRequest proposalClaimRequest) {
        Task task = taskService.createTaskQuery().taskId(proposalClaimRequest.getTaskId()).singleResult();
        taskService.claim(task.getId(), proposalClaimRequest.getUsername());

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", proposalClaimRequest.getUsername());

        taskService.complete(task.getId(), variables);
        return new ProposalClaimResponse(task.getId(), proposalClaimRequest.getUsername());
    }

    @Override
    public ProposalRejectResponse reject(String taskId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", "Deny");
        taskService.complete(taskId, variables);
        return new ProposalRejectResponse(taskId);
    }

    @Override
    public ProposalCompleteResponse complete(String taskId) {
        taskService.complete(taskId);
        return new ProposalCompleteResponse(taskId);
    }

    @Override
    public List<ProposalResponse> getByUserName(ProposalRequest proposalRequest) {

        List<Task> tasks = taskService
                .createTaskQuery()
                .taskAssignee(proposalRequest.getUsername())
                .list();

        return tasks
                .stream()
                .map(task ->{
                    ProposalResponse proposalResponse = new ProposalResponse();
                    proposalResponse.setTaskId(task.getId());
                    proposalResponse.setProcessInstanceId(task.getProcessInstanceId());
                    return proposalResponse;
                })
                .toList();
    }

    @Override
    public ProposalAllowResponse allow(String taskId, ProposalAllowRequest proposalAllowRequest) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", "Allow");
        variables.put("user", proposalAllowRequest.getUserName());
        taskService.complete(taskId, variables);
        return new ProposalAllowResponse(taskId);
    }

    @Override
    public ProposalUpdateResponse update(String taskId, ProposalUpdateRequest proposalAllowRequest) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", "Update");
        variables.put("sale_user", proposalAllowRequest.getUserName());
        taskService.complete(taskId, variables);
        return new ProposalUpdateResponse(taskId);
    }

    @Override
    public ProposalActionResponse action(String taskId, ProposalActionRequest proposalActionRequest) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", proposalActionRequest.getAction());
        variables.put("user", proposalActionRequest.getUsername());
        taskService.complete(taskId, variables);
        return new ProposalActionResponse(taskId, proposalActionRequest.getUsername());
    }
}
