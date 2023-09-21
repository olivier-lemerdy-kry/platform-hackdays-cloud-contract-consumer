package se.kry.demo.contract.consumer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FraudCheckRequest(@JsonProperty("client.id") String clientId, int loanAmount) {
}
