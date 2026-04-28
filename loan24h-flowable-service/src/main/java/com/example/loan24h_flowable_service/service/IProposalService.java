package com.example.loan24h_flowable_service.service;

import com.example.loan24h_flowable_service.payload.request.*;
import com.example.loan24h_flowable_service.payload.response.*;

import java.util.List;

public interface IProposalService {

    List<ProposalResponse> getUnassignedProposal();

    ProposalClaimResponse claim(ProposalClaimRequest proposalClaimRequest);

    ProposalRejectResponse reject(String taskId);

    ProposalCompleteResponse complete(String taskId);

    List<ProposalResponse> getByUserName(ProposalRequest proposalRequest);

    ProposalAllowResponse allow(String taskId, ProposalAllowRequest proposalAllowRequest);

    ProposalUpdateResponse update(String taskId, ProposalUpdateRequest proposalAllowRequest);

    ProposalActionResponse action(String taskId, ProposalActionRequest proposalActionRequest);

}
