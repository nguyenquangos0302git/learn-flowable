package com.example.demo01.service;

import com.example.demo01.dto.TaskResponseDto;

import java.util.List;

public interface IRuntimeTaskQueryService {

    List<TaskResponseDto> getTasks(
            String assignee,
            String processInstanceId);

}
