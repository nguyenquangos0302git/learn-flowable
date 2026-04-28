package com.example.demo01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {

    private String id;
    private String action;
    private String assignee;
    private String financalUser;

}
