package com.example.loan24h_flowable_service.api;

import com.example.loan24h_flowable_service.payload.request.*;
import com.example.loan24h_flowable_service.payload.response.*;
import com.example.loan24h_flowable_service.payload.response.common.DataResponse;
import com.example.loan24h_flowable_service.service.IProposalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proposals")
@AllArgsConstructor
public class ProposalApi {

    private final IProposalService iProposalService;

    @GetMapping("/unassigned")
    public DataResponse<List<ProposalResponse>> getUnassignedProposal() {
        DataResponse<List<ProposalResponse>> dataResponse = new DataResponse<>();
        dataResponse.setData(iProposalService.getUnassignedProposal());
        return dataResponse;
    }

    @PostMapping("/claim")
    public DataResponse<ProposalClaimResponse> claim(@RequestBody ProposalClaimRequest proposalClaimRequest) {
        DataResponse<ProposalClaimResponse> response = new DataResponse<>();
        response.setData(iProposalService.claim(proposalClaimRequest));
        return response;
    }

    @PostMapping("")
    public DataResponse<List<ProposalResponse>> getProposal(@RequestBody ProposalRequest proposalRequest) {
        DataResponse<List<ProposalResponse>> response = new DataResponse<>();
        response.setData(iProposalService.getByUserName(proposalRequest));
        return response;
    }

    @PostMapping("/{taskId}/reject")
    public DataResponse<ProposalRejectResponse> reject(@PathVariable String taskId) {
        DataResponse<ProposalRejectResponse> response = new DataResponse<>();
        response.setData(iProposalService.reject(taskId));
        return response;
    }

    @PostMapping("/{taskId}/complete")
    public DataResponse<ProposalCompleteResponse> complete(@PathVariable String taskId) {
        DataResponse<ProposalCompleteResponse> response = new DataResponse<>();
        response.setData(iProposalService.complete(taskId));
        return response;
    }

    @PostMapping("/{taskId}/allow")
    public DataResponse<ProposalAllowResponse> allow(@PathVariable String taskId, @RequestBody ProposalAllowRequest proposalAllowRequest) {
        DataResponse<ProposalAllowResponse> response = new DataResponse<>();
        response.setData(iProposalService.allow(taskId, proposalAllowRequest));
        return response;
    }

    @PostMapping("/{taskId}/update")
    public DataResponse<ProposalUpdateResponse> update(@PathVariable String taskId, @RequestBody ProposalUpdateRequest proposalUpdateRequest) {
        DataResponse<ProposalUpdateResponse> response = new DataResponse<>();
        response.setData(iProposalService.update(taskId, proposalUpdateRequest));
        return response;
    }

    @PostMapping("/{taskId}/action")
    public DataResponse<ProposalActionResponse> action(@PathVariable String taskId, @RequestBody ProposalActionRequest proposalActionRequest) {
        DataResponse<ProposalActionResponse> response = new DataResponse<>();
        response.setData(iProposalService.action(taskId, proposalActionRequest));
        return response;
    }

}
