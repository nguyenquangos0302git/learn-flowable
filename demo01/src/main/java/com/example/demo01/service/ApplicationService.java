package com.example.demo01.service;

import com.example.demo01.model.TaskDetails;
import com.example.demo01.model.Employee;
import com.example.demo01.model.TaskInfo;

import java.util.List;

public interface ApplicationService {

    void deployProcessDefinition();

    void initiateWorkflow(Employee employee, String processDefKey);

    void completeUserTask(TaskInfo taskInfo);

    void claimTask(TaskInfo taskInfo);

    void unclaimTask(TaskInfo taskInfo);

    List<TaskDetails> getUserTasks();
}
