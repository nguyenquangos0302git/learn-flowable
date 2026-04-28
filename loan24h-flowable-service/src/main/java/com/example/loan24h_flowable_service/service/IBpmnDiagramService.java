package com.example.loan24h_flowable_service.service;

import com.example.loan24h_flowable_service.payload.request.LoanProposalRequest;
import com.example.loan24h_flowable_service.payload.request.ProposalClaimRequest;
import com.example.loan24h_flowable_service.payload.response.BpmnDeployResponse;
import com.example.loan24h_flowable_service.payload.response.ProposalClaimResponse;
import com.example.loan24h_flowable_service.payload.response.StartWorkflowResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IBpmnDiagramService {

    BpmnDeployResponse deploy(MultipartFile file) throws IOException;

    StartWorkflowResponse start(LoanProposalRequest loanProposalRequest);

    byte[] generateDiagram(String processInstanceId) throws IOException;

}
