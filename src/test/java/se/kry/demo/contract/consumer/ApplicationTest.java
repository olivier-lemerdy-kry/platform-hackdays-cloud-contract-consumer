package se.kry.demo.contract.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.kry.demo.contract.consumer.service.FraudCheckServiceURI;

@SpringBootTest
@AutoConfigureStubRunner(
    ids = {"se.kry:platform-hackdays-cloud-contract-producer:+:stubs"},
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ApplicationTest {

  @Test
  void contextLoads(@Autowired FraudCheckServiceURI fraudCheckServiceURI) {
    var restTemplate = new RestTemplate();
    var payload = """
        {
          "client.id": "1234567890",
          loanAmount: 99999
        }
        """;
    var httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

    ResponseEntity<String> response = restTemplate.exchange(
        fraudCheckServiceURI.apiURI(), HttpMethod.PUT,
        new HttpEntity<>(payload, httpHeaders), String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("FRAUD");
  }

  @TestConfiguration
  static class ApplicationTestConfiguration {

    @Bean
    @Primary
    FraudCheckServiceURI testFraudCheckServiceURI(StubFinder stubFinder) throws URISyntaxException {
      return new FraudCheckServiceURI(
          stubFinder.findStubUrl("platform-hackdays-cloud-contract-producer").toURI());
    }

  }

}
