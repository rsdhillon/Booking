package com.ba;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

/**
 * Factory class to create beans for Update Membership BS SDU.
 */
@Configuration
public class ApplicationBeanFactory extends WebMvcConfigurerAdapter {

	  /**
	   * Custom message converter used for marshaling and un-marshaling.
	   * 
	   * @return MappingJackson2HttpMessageConverter
	   */
	  @Bean
	  public MappingJackson2HttpMessageConverter jsonConverter() {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(String.class, new StringSerializer());
	    ObjectMapper mapper = new ObjectMapper().registerModule(simpleModule);
	    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	    converter.setObjectMapper(mapper);
	    return converter;
	  }

	  /*
	   * (non-Javadoc)
	   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	   * #configureMessageConverters(java.util.List)
	   */
	  @Override
	  public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
	    converters.add(jsonConverter());
	  }
 
}
