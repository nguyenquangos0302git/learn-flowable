package com.example.demo01.service.impl;

import com.example.demo01.dto.TaskActionRequest;
import com.example.demo01.service.IApprovalAuditService;
import com.example.demo01.service.IApprovalTaskService;
import com.example.demo01.service.IDelegationService;
import lombok.AllArgsConstructor;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApprovalTaskServiceImpl implements IApprovalTaskService {

    private final TaskService taskService;
    private final IApprovalAuditService auditService;
    private final IDelegationService delegationService;

    @Override
    public void handle(String taskId, TaskActionRequest req) {

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        switch (req.getAction()) {
            case APPROVE -> approve(task, req);
            case REJECT -> reject(task, req);
            case DELEGATE -> delegate(task, req);
        }

    }

    private void approve(Task task, TaskActionRequest req) {

        taskService.claim(task.getId(), req.getUserId());
        taskService.complete(task.getId());

//        auditService.audit(task, req.getUserId(),
//                ApprovalAudit.Action.APPROVE,
//                req.getComment(), null);
    }

    private void reject(Task task, TaskActionRequest req) {

        taskService.claim(task.getId(), req.getUserId());

//        auditService.audit(task, req.getUserId(),
//                ApprovalAudit.Action.REJECT,
//                req.getComment(), null);

        throw new BpmnError("REJECT");
    }

    private void delegate(Task task, TaskActionRequest req) {

//        delegationService.delegate(
//                task,
//                req.getUserId(),
//                req.getDelegateTo()
//        );

//        auditService.audit(task, req.getUserId(),
//                ApprovalAudit.Action.DELEGATE,
//                req.getComment(),
//                req.getDelegateTo());
    }
}
