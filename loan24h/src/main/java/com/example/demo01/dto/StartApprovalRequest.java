package com.example.demo01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartApprovalRequest {

    private String documentId;

    private List<ApprovalLevelDto> levels;

}
