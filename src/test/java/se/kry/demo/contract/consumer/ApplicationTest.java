package se.kry.demo.contract.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureStubRunner(
    ids = {"se.kry:platform-hackdays-cloud-contract-producer:+:stubs:9090"},
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ApplicationTest {

  @Test
  void contextLoads() {
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
        "http://localhost:" + 9090 + "/fraudcheck", HttpMethod.PUT,
        new HttpEntity<>(payload, httpHeaders), String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("FRAUD");
  }

}
