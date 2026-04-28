package com.example.demo01.service.impl;

import com.example.demo01.dto.TaskResponseDto;
import com.example.demo01.service.IRuntimeTaskQueryService;
import lombok.AllArgsConstructor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RuntimeTaskQueryService implements IRuntimeTaskQueryService {

    private final TaskService taskService;

    @Override
    public List<TaskResponseDto> getTasks(String assignee, String processInstanceId) {
        TaskQuery query = taskService.createTaskQuery();

        if (assignee != null && !assignee.isBlank()) {
            query = query.taskAssignee(assignee);
        }

        if (processInstanceId != null && !processInstanceId.isBlank()) {
            query = query.processInstanceId(processInstanceId);
        }

        return query
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private TaskResponseDto toDto(Task task) {

        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setAssignee(task.getAssignee());
        dto.setProcessInstanceId(task.getProcessInstanceId());
        dto.setProcessDefinitionId(task.getProcessDefinitionId());
        dto.setCreateTime(task.getCreateTime());
        dto.setDueDate(task.getDueDate());
        dto.setTaskDefinitionKey(task.getTaskDefinitionKey());

        return dto;
    }

}
