package com.app.bcpcixchallenge.exchange.repositories;

import com.app.bcpcixchallenge.exchange.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findByCode(String code);
}
