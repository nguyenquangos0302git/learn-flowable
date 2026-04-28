package com.example.demo01.servicetask;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateProposalStatusService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("update proposal status");
    }

}
