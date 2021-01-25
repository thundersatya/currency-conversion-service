package com.satya.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.satya.model.CurrencyConversionBean;

//@RibbonClient(name="currency-exchange-service") 
//@FeignClient(name="currency-exchange-service", url = "http://localhost:5099") - removed to 
@FeignClient(name="netflix-zuul-api-gateway-server")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")       //where {from} and {to} are path variable  
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to); //from map to USD and to map to INR  
}
