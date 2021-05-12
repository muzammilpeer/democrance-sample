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
public class QuoteDTO {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long policyId;
    private Long customerId;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private long premium;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private long cover;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String state;
}