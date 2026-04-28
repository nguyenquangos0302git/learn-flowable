package com.example.demo01.service.impl;

import com.example.demo01.model.TaskDetails;
import com.example.demo01.model.Employee;
import com.example.demo01.model.TaskInfo;
import com.example.demo01.service.ApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;

    @Override
    public void deployProcessDefinition() {
        Deployment deployment = repositoryService
                .createDeployment()
//                .addClasspathResource("processes/onboarding.bpmn20.xml")
                .addClasspathResource("processes/multi-level-approval.bpmn20.xml")
                .deploy();
        log.info("deploy instance id: {}", deployment.getId());
    }

    @Override
    public void initiateWorkflow(Employee employee, String processDefKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee.getEmployee());
        variables.put("description", employee.getDescription());
        variables.put("nrOfHolidays", employee.getNrOfHolidays());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefKey, variables);

        log.info("initiate process instance id: {}", processInstance.getProcessInstanceId());
    }

    @Override
    public void completeUserTask(TaskInfo taskInfo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", taskInfo.getApproval());
        taskService.complete(taskInfo.getId(), variables);
    }

    @Override
    public void claimTask(TaskInfo taskInfo) {
        taskService.claim(taskInfo.getId(), taskInfo.getAssignee());
    }

    @Override
    public void unclaimTask(TaskInfo taskInfo) {
        taskService.unclaim(taskInfo.getId());
    }

    @Override
    public List<TaskDetails> getUserTasks() {
        List<Task> tasks = taskService.createTaskQuery().taskUnassigned().list();
        return getTaskDetails(tasks);
    }

    private List<TaskDetails> getTaskDetails(List<Task> tasks) {
        List<TaskDetails> taskDetails = new ArrayList<>();

        for (Task task : tasks) {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            TaskDetails taskDetail = new TaskDetails();
            taskDetail.setId(task.getId());
            taskDetail.setName(task.getName());
//            taskDetail.setCreateTime(task.getCreateTime().toString());
//            taskDetail.setDueDate(task.getDueDate().toString());
            taskDetail.setTaskDefinition(task.getTaskDefinitionKey());
            taskDetails.add(taskDetail);
        }

        return taskDetails;
    }
}
