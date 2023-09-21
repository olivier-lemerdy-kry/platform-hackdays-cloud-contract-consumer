package se.kry.demo.contract.consumer.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FraudCheckRequest(@JsonProperty("client.id") String clientId, int loanAmount) {
}
