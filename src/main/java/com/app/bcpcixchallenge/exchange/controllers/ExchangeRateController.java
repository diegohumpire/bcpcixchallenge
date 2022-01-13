package com.app.bcpcixchallenge.exchange.controllers;

import com.app.bcpcixchallenge.exchange.ExchangeRate;
import com.app.bcpcixchallenge.exchange.ExchangeRateRequest;
import com.app.bcpcixchallenge.exchange.ExchangeRateResponse;
import com.app.bcpcixchallenge.exchange.exceptions.CurrencyNotFoundRunTimeException;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/exchange") // TODO: Add base prefix and Version
public class ExchangeRateController {

    @Autowired
    private ExchangeRate exchangeRate;

    @PostMapping(value = "", produces = "application/json")
    public ExchangeRateResponse exchangeRate(@Valid @RequestBody ExchangeRateRequest request) {
        return this.exchangeRate.convert(request.getFrom(), request.getTo(), request.getAmount());
    }

    @PostMapping(value = "/rx", produces = "application/json")
    public Single<ResponseEntity<ExchangeRateResponse>> singleResponse(@Valid @RequestBody ExchangeRateRequest request) {
        ExchangeRateResponse exchangeRateResponse = this.exchangeRate.convert(request.getFrom(), request.getTo(), request.getAmount());
        ResponseEntity<ExchangeRateResponse> response = new ResponseEntity<ExchangeRateResponse>(exchangeRateResponse, HttpStatus.CREATED);

        return Single.just(response);
    }

    @ExceptionHandler({ CurrencyNotFoundRunTimeException.class })
    public ResponseEntity<Object> handleException(HttpServletRequest req, Exception ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("error", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
    }
}
