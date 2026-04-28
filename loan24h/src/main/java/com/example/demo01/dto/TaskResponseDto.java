package com.example.demo01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskResponseDto {

    private String id;
    private String name;
    private String assignee;
    private String processInstanceId;
    private String processDefinitionId;

    private Date createTime;
    private Date dueDate;

    private String taskDefinitionKey;

}
