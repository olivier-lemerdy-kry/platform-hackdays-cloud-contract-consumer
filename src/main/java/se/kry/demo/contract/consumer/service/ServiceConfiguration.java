package se.kry.demo.contract.consumer.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ServiceConfiguration {

  @Bean
  FraudCheckServiceURI fraudCheckServiceUrl(@Value("${services.fraudcheck.baseUrl}") URI baseURI) {
    return new FraudCheckServiceURI(baseURI);
  }

  @Bean
  FraudCheckService fraudCheckService(FraudCheckServiceURI fraudCheckServiceURI) {
    var client = WebClient.builder().baseUrl(fraudCheckServiceURI.baseURI().toString()).build();
    return HttpServiceProxyFactory
        .builder(WebClientAdapter.forClient(client))
        .build()
        .createClient(FraudCheckService.class);
  }

}
