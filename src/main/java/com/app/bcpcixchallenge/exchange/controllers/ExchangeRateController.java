package com.app.bcpcixchallenge.exchange.controllers;

import com.app.bcpcixchallenge.exchange.ExchangeRate;
import com.app.bcpcixchallenge.exchange.ExchangeRateRequest;
import com.app.bcpcixchallenge.exchange.ExchangeRateResponse;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
