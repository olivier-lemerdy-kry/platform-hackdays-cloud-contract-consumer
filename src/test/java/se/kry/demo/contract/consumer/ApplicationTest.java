package se.kry.demo.contract.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureStubRunner(
    ids = {"se.kry:platform-hackdays-cloud-contract-producer:+:stubs"},
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ApplicationTest {

  @Test
  void contextLoads(@Autowired StubFinder stubFinder) {
    var restTemplate = new RestTemplate();
    var payload = """
        {
          "client.id": "1234567890",
          loanAmount: 99999
        }
        """;
    var httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

    var port = stubFinder.findAllRunningStubs().getPort("platform-hackdays-cloud-contract-producer");
    ResponseEntity<String> response = restTemplate.exchange(
        "http://localhost:" + port + "/fraudcheck", HttpMethod.PUT,
        new HttpEntity<>(payload, httpHeaders), String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("FRAUD");
  }

}
