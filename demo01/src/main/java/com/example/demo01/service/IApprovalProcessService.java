package com.example.demo01.service;

import com.example.demo01.dto.StartApprovalRequest;
import org.flowable.engine.runtime.ProcessInstance;

public interface IApprovalProcessService {

    ProcessInstance start(StartApprovalRequest request);

}
