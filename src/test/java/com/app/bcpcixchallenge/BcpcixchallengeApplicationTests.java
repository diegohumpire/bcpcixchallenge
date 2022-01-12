package com.app.bcpcixchallenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.app.bcpcixchallenge.exchange.ExchangeRate;
import com.app.bcpcixchallenge.exchange.dto.CurrencyDTO;
import com.app.bcpcixchallenge.exchange.entities.Currency;
import com.app.bcpcixchallenge.exchange.exceptions.CurrencyNotFoundRunTimeException;
import com.app.bcpcixchallenge.exchange.repositories.CurrencyRepository;
import com.app.bcpcixchallenge.exchange.services.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class BcpcixchallengeApplicationTests {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private ExchangeRate exchangeRate;

	@Autowired
	private CurrencyService currencyService;

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldGetRateValueFromCodeCurrency() {
		Currency currency = this.currencyRepository.findByCode("PEN");
		assertThat(currency).isNotNull();
	}

	@Test
	public void shouldThrowNotFoundCurrency() {
		Currency currency = this.currencyRepository.findByCode("XXXX");
		assertThat(currency).isNull();
	}

	@Test
	public void shouldConvertPENtoEUR() {
		Currency PEN = this.currencyRepository.findByCode("PEN");

		BigDecimal EURvalue = exchangeRate.convertToBase(10.0, PEN);

		assertThat(EURvalue).isPositive();
		assertThat(EURvalue.toString()).isEqualTo("2.2515991");
	}

	@Test
	public void shouldConvertEURtoPEN() {
		Currency PEN = this.currencyRepository.findByCode("PEN");

		BigDecimal EURvalue = exchangeRate.convertFromBase(10.0, PEN);

		assertThat(EURvalue).isPositive();
		assertThat(EURvalue.toString()).isEqualTo("44.41288");
	}

	@Test
	public void shouldConvertPENtoARS() {
		BigDecimal value = exchangeRate.convert("PEN", "ARS", 10.0);

		assertThat(value).isPositive();
		assertThat(value.toString()).isEqualTo("264.7965");
	}

	@Test
	public void shouldFindByCode() {
		Currency PEN = this.currencyService.findByCode("PEN");
		assertThat(PEN).isNotNull();

		assertThatExceptionOfType(CurrencyNotFoundRunTimeException.class)
				.isThrownBy(() -> {
					this.currencyService.findByCode("XXXXX");
				});
	}

	@Test
	public void shouldCreateNewCurrency() {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setCode("NEW");
		currencyDTO.setRate(1.0505);

		this.currencyService.saveOrUpdate(currencyDTO);

		Currency NEW_CURRENCY = this.currencyService.findByCode("NEW");

		assertThat(NEW_CURRENCY).isNotNull();
		assertThat(NEW_CURRENCY.getRate().stripTrailingZeros().toString()).isEqualTo("1.0505");
	}

	@Test
	public void shouldUpdateCurrency() {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setCode("NEW1");
		currencyDTO.setRate(1.0505);

		this.currencyService.saveOrUpdate(currencyDTO);

		Currency NEW_CURRENCY = this.currencyService.findByCode("NEW1");

		assertThat(NEW_CURRENCY).isNotNull();

		currencyDTO.setRate(2.0303);
		this.currencyService.updateByCode("NEW1", currencyDTO);

		Currency UPDATED_CURRENCY = this.currencyService.findByCode("NEW1");

		assertThat(NEW_CURRENCY).isNotNull();
		assertThat(UPDATED_CURRENCY).isNotEqualTo(NEW_CURRENCY);
		assertThat(UPDATED_CURRENCY.getRate()).isNotEqualTo(NEW_CURRENCY.getRate());
	}
}
