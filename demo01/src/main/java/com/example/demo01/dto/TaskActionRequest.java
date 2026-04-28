package com.example.demo01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskActionRequest {

    private String userId;

    // APPROVE | REJECT | DELEGATE
    private Action action;

    // dùng cho reject / delegate
    private String comment;

    // chỉ dùng khi DELEGATE
    private String delegateTo;

    public enum Action {
        APPROVE,
        REJECT,
        DELEGATE
    }

}
