package com.example.demo01.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "approval_audit")
@Getter
@Setter
public class ApprovalAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String processInstanceId;
    private String taskId;

    private String userId;
    private String delegatedTo;

    private Integer levelIndex;

    @Enumerated(EnumType.STRING)
    private Action action;

    private LocalDateTime actionTime;

    private String comment;

    public enum Action {
        APPROVE, REJECT, DELEGATE
    }

}
