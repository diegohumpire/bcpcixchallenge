package com.app.bcpcixchallenge.exchange;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ExchangeRateRequest {
    @NotNull(message = "`amount` is required")
    @Positive(message = "`amount` is required and must be greater than 0")
    private double amount;

    @NotNull(message = "`From` currency can't be null")
    @NotBlank(message = "`From` currency code is required")
    private String from;

    @NotNull(message = "`To` field can't be null")
    @NotBlank(message = "`To` field is required. Please fill with valid currency code")
    private String to;
}
