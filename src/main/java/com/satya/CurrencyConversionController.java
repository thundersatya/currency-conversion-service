package com.satya;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.satya.feign.CurrencyExchangeServiceProxy;
import com.satya.model.CurrencyConversionBean;

@RestController
public class CurrencyConversionController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	private Logger logger=LoggerFactory.getLogger(this.getClass()); 
	//With resttemaplate
	@GetMapping("/currency-converter-with-resttemplate/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency1(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<>();

		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity(
				"http://localhost:5076/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		// creating a new response bean and getting the response back and taking it into
		// Bean
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	//With FeignClinet
		@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
		public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
				@PathVariable BigDecimal quantity) {
			CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
			logger.info("{}", response);  
			return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
					quantity.multiply(response.getConversionMultiple()), response.getPort());
		}
	
	
}
