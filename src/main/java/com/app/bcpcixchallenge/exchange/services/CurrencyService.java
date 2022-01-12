package com.app.bcpcixchallenge.exchange.services;

import com.app.bcpcixchallenge.exchange.ExchangeRate;
import com.app.bcpcixchallenge.exchange.dto.CurrencyDTO;
import com.app.bcpcixchallenge.exchange.entities.Currency;
import com.app.bcpcixchallenge.exchange.exceptions.CurrencyNotFoundRunTimeException;
import com.app.bcpcixchallenge.exchange.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyService implements ICurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency findByCode(String code) {
        Currency currency = this.currencyRepository.findByCode(code);

        if (currency == null) {
            throw new CurrencyNotFoundRunTimeException();
        }

        return currency;
    }

    @Override
    public void saveOrUpdate(CurrencyDTO currency) {
        Currency currencyEntity = new Currency();
        currencyEntity.setCode(currency.getCode());
        currencyEntity.setRate(
                new BigDecimal(currency.getRate(), new MathContext(ExchangeRate.SCALE, RoundingMode.HALF_UP))
                        .stripTrailingZeros()
        );

        this.currencyRepository.save(currencyEntity);
    }

    @Override
    public void updateByCode(String code, CurrencyDTO currency) {
        Currency currencyEntity = this.findByCode(code);

        currencyEntity.setRate(
                new BigDecimal(currency.getRate(), new MathContext(ExchangeRate.SCALE, RoundingMode.HALF_UP))
                        .stripTrailingZeros()
        );

        this.currencyRepository.save(currencyEntity);
    }

    @Override
    public List<Currency> listAll() {
        return this.currencyRepository.findAll();
    }
}
