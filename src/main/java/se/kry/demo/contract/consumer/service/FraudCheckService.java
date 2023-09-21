package se.kry.demo.contract.consumer.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PutExchange;
import se.kry.demo.contract.consumer.domain.FraudCheckRequest;
import se.kry.demo.contract.consumer.domain.FraudCheckResponse;

public interface FraudCheckService {

  @PutExchange("/fraudcheck")
  FraudCheckResponse fraudCheck(@RequestBody FraudCheckRequest fraudCheckRequest);
}
