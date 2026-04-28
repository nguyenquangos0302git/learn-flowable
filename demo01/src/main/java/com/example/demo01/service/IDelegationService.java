package com.example.demo01.service;

import org.flowable.task.api.Task;

public interface IDelegationService {

    void delegate(Task task, String fromUser, String toUser);

}
