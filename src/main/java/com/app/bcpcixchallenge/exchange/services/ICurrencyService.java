package com.app.bcpcixchallenge.exchange.services;

import com.app.bcpcixchallenge.exchange.dto.CurrencyDTO;
import com.app.bcpcixchallenge.exchange.entities.Currency;

import java.util.List;

public interface ICurrencyService {
    Currency findByCode(String code);
    void saveOrUpdate(CurrencyDTO currency);
    void updateByCode(String code, CurrencyDTO currency);
    List<Currency> listAll();
}
