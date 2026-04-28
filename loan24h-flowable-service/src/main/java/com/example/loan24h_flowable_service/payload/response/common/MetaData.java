package com.example.loan24h_flowable_service.payload.response.common;

import com.example.loan24h_flowable_service.constants.PatternFormatConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaData {

    @JsonProperty("request_id")
    private String requestId;

    private String signature;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternFormatConstants.DATE_TIME_FORMAT)
    private String timestamp;

    private String path;

}
