package com.example.demo01.controller;

import com.example.demo01.model.TaskDetails;
import com.example.demo01.model.Employee;
import com.example.demo01.model.TaskInfo;
import com.example.demo01.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class ProcessController {

    private final ApplicationService applicationService;

    @PostMapping("/deploy")
    public void deployWorkflow() {
        applicationService.deployProcessDefinition();
    }

    @PostMapping("/createVaccinationRequest")
    public void createProcessInstance(@RequestBody Employee employee) {
        String processDefKey = "onboarding";
        applicationService.initiateWorkflow(employee, processDefKey);
    }

    @PostMapping("/completeTask")
    public void completeTask(@RequestBody TaskInfo taskInfo) {
        applicationService.completeUserTask(taskInfo);
    }

    @PostMapping("/claimTask")
    public void claimTask(@RequestBody TaskInfo taskInfo) {
        applicationService.claimTask(taskInfo);
    }

    @PostMapping("/unclaimTask")
    public void unclaimTask(@RequestBody TaskInfo taskInfo) {
        applicationService.unclaimTask(taskInfo);
    }

    @GetMapping("/getPendingTasks")
    public List<TaskDetails> getUserTasks() {
        return applicationService.getUserTasks();
    }

}
