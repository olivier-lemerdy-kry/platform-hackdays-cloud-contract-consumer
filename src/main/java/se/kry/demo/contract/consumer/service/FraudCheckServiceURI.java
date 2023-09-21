package se.kry.demo.contract.consumer.service;

import java.net.URI;

public record FraudCheckServiceURI(URI baseUri) {

  public URI apiURI() {
    return baseUri.resolve("/fraudcheck");
  }

}
