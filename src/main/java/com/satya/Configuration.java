package com.satya;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@Component
public class Configuration {
	@Bean
	RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	@Bean  
	//creating a sampler called always sampler  
	public Sampler defaultSampler()  
	{  
	return Sampler.ALWAYS_SAMPLE;  
	}  

}
