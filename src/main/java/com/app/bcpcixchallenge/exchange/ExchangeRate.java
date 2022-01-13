package com.app.bcpcixchallenge.exchange;

import com.app.bcpcixchallenge.exchange.entities.Currency;
import com.app.bcpcixchallenge.exchange.exceptions.CurrencyNotFoundRunTimeException;
import com.app.bcpcixchallenge.exchange.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class ExchangeRate {
    public static final int SCALE = 7;

    @Autowired
    private CurrencyRepository currencyRepository;
    private String base;

    public ExchangeRate() {
        this.base = "EUR";
    }

    public ExchangeRateResponse convert(String from, String to, double amount) {

        // toUppercase
        from = from.toUpperCase();
        to = to.toUpperCase();

        Currency currencyFrom = this.currencyRepository.findByCode(from);
        Currency currencyTo = this.currencyRepository.findByCode(to);

        if (currencyFrom == null || currencyTo == null) {
            throw new CurrencyNotFoundRunTimeException("Some currency code is not valid");
        }

        // Ex: PEN to ARS
        // 1. Convert PEN to EUR
        BigDecimal toBase = this.convertToBase(amount, currencyFrom);

        // 2. Convert EUR to ARS
        BigDecimal fromBase = this.convertFromBase(toBase.doubleValue(), currencyTo);

        ExchangeRateResponse response = new ExchangeRateResponse();

        response.setExchangeRate(this.getExchangeRate(currencyFrom, currencyTo));
        response.setAmount(amount);
        response.setFrom(currencyFrom.getCode());
        response.setTo(currencyTo.getCode());
        response.setAmountCalculated(fromBase);

        return response;
    }

    // TODO: Get base dynamically
    public BigDecimal convertToBase(double amount, Currency from) {
        BigDecimal amountBig = BigDecimal.valueOf(amount);
        BigDecimal amountConverted = amountBig.divide(from.getRate(), SCALE, RoundingMode.HALF_UP);

        return amountConverted;
    }

    // TODO: Get base dynamically
    public BigDecimal convertFromBase(double amount, Currency to) {
        BigDecimal amountBig = BigDecimal.valueOf(amount);
        BigDecimal amountConverted = amountBig.multiply(to.getRate(), new MathContext(SCALE, RoundingMode.HALF_UP));

        return amountConverted;
    }

    public BigDecimal getExchangeRate(Currency from, Currency to) {
        return to.getRate().divide(from.getRate(), SCALE, RoundingMode.HALF_UP);
    }
}
