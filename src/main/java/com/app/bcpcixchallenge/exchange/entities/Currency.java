package com.app.bcpcixchallenge.exchange.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String base = "EUR";
    private BigDecimal rate = new BigDecimal(1);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRate() {
        return rate.stripTrailingZeros();
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String toString() {
        return "Currency{ " + "rate=" + this.rate.stripTrailingZeros().toString() + "}";
    }
}
