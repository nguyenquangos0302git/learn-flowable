package com.example.demo01.service;

import com.example.demo01.dto.TaskActionRequest;

public interface IApprovalTaskService {

    void handle(String taskId, TaskActionRequest req);

}
