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
import se.kry.demo.contract.consumer.domain.FraudCheckRequest;
import se.kry.demo.contract.consumer.service.FraudCheckService;
import se.kry.demo.contract.consumer.service.FraudCheckServiceURI;
import se.kry.demo.contract.consumer.domain.FraudCheckStatus;

@SpringBootTest
@AutoConfigureStubRunner(
    ids = {"se.kry:platform-hackdays-cloud-contract-producer:+:stubs"},
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ApplicationTest {

  @Test
  void contextLoads(@Autowired FraudCheckService fraudCheckService) {
    var request = new FraudCheckRequest("1234567890", 99999);

    var response = fraudCheckService.fraudCheck(request);

    assertThat(response.fraudCheckStatus()).isEqualTo(FraudCheckStatus.FRAUD);
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
