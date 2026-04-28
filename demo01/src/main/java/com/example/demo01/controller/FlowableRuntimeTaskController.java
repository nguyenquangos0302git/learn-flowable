package com.example.demo01.controller;

import com.example.demo01.dto.TaskResponseDto;
import com.example.demo01.service.IRuntimeTaskQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flowable/runtime")
@RequiredArgsConstructor
public class FlowableRuntimeTaskController {

    private final IRuntimeTaskQueryService taskQueryService;

    @GetMapping("/tasks")
    public List<TaskResponseDto> getRuntimeTasks(
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String processInstanceId) {

        return taskQueryService.getTasks(assignee, processInstanceId);
    }

}
