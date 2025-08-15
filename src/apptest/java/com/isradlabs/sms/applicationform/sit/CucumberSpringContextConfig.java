package com.isradlabs.sms.applicationform.sit;



import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import lombok.extern.log4j.Log4j2;

@Log4j2
@TestConfiguration
public class CucumberSpringContextConfig {
  /*
  @Bean
  public CustomLogger customLogger() {
      return new CustomLogger();
  }
*/
  
  @Bean
  public RestTemplate restTemplate() {
      log.info("Creating new RestTemplate bean");
      return new RestTemplate();
  }
}