package com.example.loan24h_flowable_service.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDeployResponse {

    @JsonProperty("deployment_id")
    private String deploymentId;

}
