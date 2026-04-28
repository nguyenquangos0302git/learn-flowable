package com.example.loan24h_flowable_service.service_task;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoanProposalService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        log.info("Processing loan: {}", execution.toString());
    }
}
