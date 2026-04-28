package com.example.demo01.controller;

import com.example.demo01.dto.StartApprovalRequest;
import com.example.demo01.dto.TaskActionRequest;
import com.example.demo01.service.IApprovalProcessService;
import com.example.demo01.service.IApprovalTaskService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final IApprovalProcessService processService;
    private final IApprovalTaskService taskService;

    @PostMapping("/start")
    public ResponseEntity<?> start(
            @RequestBody StartApprovalRequest request) {

        ProcessInstance pi = processService.start(request);
        return ResponseEntity.ok(pi.getId());
    }

    @PostMapping("/tasks/{taskId}/action")
    public ResponseEntity<?> action(
            @PathVariable String taskId,
            @RequestBody TaskActionRequest request) {

        taskService.handle(taskId, request);
        return ResponseEntity.ok().build();
    }

}
