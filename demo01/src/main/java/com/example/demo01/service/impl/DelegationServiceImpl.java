package com.example.demo01.service.impl;

import com.example.demo01.service.IDelegationService;
import lombok.AllArgsConstructor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DelegationServiceImpl implements IDelegationService {

    private final TaskService taskService;

    @Override
    public void delegate(Task task, String fromUser, String toUser) {
        taskService.claim(task.getId(), fromUser);

        // Flowable hỗ trợ delegate chuẩn BPMN
        taskService.delegateTask(task.getId(), toUser);
    }
}
