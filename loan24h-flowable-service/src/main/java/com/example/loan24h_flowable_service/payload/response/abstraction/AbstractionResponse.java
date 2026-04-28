package com.example.loan24h_flowable_service.payload.response.abstraction;

import com.example.loan24h_flowable_service.payload.response.common.MetaData;
import com.example.loan24h_flowable_service.payload.response.common.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractionResponse {

    @JsonProperty("status")
    private ResponseStatus responseStatus;

    @JsonProperty("meta_data")
    private MetaData metaData;

}
