package com.app.bcpcixchallenge.exchange.controllers;

import com.app.bcpcixchallenge.exchange.dto.CurrencyDTO;
import com.app.bcpcixchallenge.exchange.entities.Currency;
import com.app.bcpcixchallenge.exchange.services.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/currencies") // TODO: Add base prefix and Version
public class CurrencyController {

    @Autowired
    private ICurrencyService currencyService;

    @GetMapping(value = "", produces = "application/json")
    public List<Currency> listAll() {
        return this.currencyService.listAll();
    }

    @GetMapping(value = "/{code}", produces = "application/json")
    public Currency findByCode(@PathVariable String code) {
        return this.currencyService.findByCode(code);
    }

    @PutMapping(value = "/{code}", produces = "application/json")
    public Currency updateCurrencyByCode(@PathVariable String code, @RequestBody CurrencyDTO currencyDTO) {
        this.currencyService.updateByCode(code, currencyDTO);

        return this.currencyService.findByCode(code);
    }
}
