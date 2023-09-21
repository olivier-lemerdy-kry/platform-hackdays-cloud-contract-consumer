package se.kry.demo.contract.consumer.service;

import java.net.URI;

public record FraudCheckServiceURI(URI baseURI) {

  public URI apiURI() {
    return baseURI.resolve("/fraudcheck");
  }

}
