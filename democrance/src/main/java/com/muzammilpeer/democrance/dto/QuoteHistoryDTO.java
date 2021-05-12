package com.muzammilpeer.democrance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuoteHistoryDTO {
    private Long policyHistoryId;
    private Long policyId;
    private String policyStateName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String quoteLogs;
}