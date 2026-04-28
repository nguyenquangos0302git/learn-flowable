package com.example.demo01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDetails {

    private String id;
    private String name;
    private String createTime;
    private String dueDate;
    private String taskDefinition;

}
