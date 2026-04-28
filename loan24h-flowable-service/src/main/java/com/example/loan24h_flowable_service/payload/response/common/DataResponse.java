package com.example.loan24h_flowable_service.payload.response.common;

import com.example.loan24h_flowable_service.payload.response.abstraction.AbstractionResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> extends AbstractionResponse {

    private T data;

}
