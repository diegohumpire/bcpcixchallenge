package com.app.bcpcixchallenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.bcpcixchallenge.exchange.ExchangeRate;
import com.app.bcpcixchallenge.exchange.ExchangeRateRequest;
import com.app.bcpcixchallenge.exchange.ExchangeRateResponse;
import com.app.bcpcixchallenge.exchange.dto.CurrencyDTO;
import com.app.bcpcixchallenge.exchange.entities.Currency;
import com.app.bcpcixchallenge.exchange.exceptions.CurrencyNotFoundRunTimeException;
import com.app.bcpcixchallenge.exchange.repositories.CurrencyRepository;
import com.app.bcpcixchallenge.exchange.services.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BcpcixchallengeApplicationTests {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private ExchangeRate exchangeRate;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

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
		ExchangeRateResponse value = exchangeRate.convert("PEN", "ARS", 10.0);

		assertThat(value.getAmountCalculated()).isPositive();
		assertThat(value.getAmountCalculated().toString()).isEqualTo("264.7965");
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

	@Test
	public void shouldConvertAmountCorrectly() throws Exception {
		ExchangeRateRequest requestContent = new ExchangeRateRequest();
		requestContent.setAmount(10);
		requestContent.setFrom("PEN");
		requestContent.setTo("ARS");

		mvc.perform(
					post("/api/exchange")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(requestContent))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.amount", is(10.0)))
				.andExpect(jsonPath("$.from", is("PEN")))
				.andExpect(jsonPath("$.to", is("ARS")));
	}

	@Test
	public void shouldThrowBadRequestWhenTryToConvert() throws Exception {
		ExchangeRateRequest requestContent = new ExchangeRateRequest();
		requestContent.setFrom("PEN");
		requestContent.setTo("ARS");

		mvc.perform(
						post("/api/exchange")
								.contentType(MediaType.APPLICATION_JSON)
								.content(this.objectMapper.writeValueAsString(requestContent))
				)
				.andExpect(status().isBadRequest());
	}
}
