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
public class CustomerDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long customerId;
    private String firstName;
    private String lastName;
    private String dob;

    public CustomerDTO(String firstName, String lastName, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
}