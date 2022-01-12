package com.app.bcpcixchallenge.exchange;

import java.math.BigDecimal;

public class ExchangeRateResponse {
    private String from;
    private String to;
    private BigDecimal exchangeRate;
    private double amount;
    private BigDecimal amountCalculated;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmountCalculated() {
        return amountCalculated;
    }

    public void setAmountCalculated(BigDecimal amountCalculated) {
        this.amountCalculated = amountCalculated;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
