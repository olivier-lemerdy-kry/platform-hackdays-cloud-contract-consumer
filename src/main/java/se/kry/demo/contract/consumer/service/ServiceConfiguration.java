package se.kry.demo.contract.consumer.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {

  @Bean
  FraudCheckServiceURI fraudCheckServiceUrl(@Value("${services.fraudcheck.baseUrl}") URI baseUri) {
    return new FraudCheckServiceURI(baseUri);
  }

}
