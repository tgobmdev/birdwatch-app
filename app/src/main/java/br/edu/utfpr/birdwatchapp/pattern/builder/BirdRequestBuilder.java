package br.edu.utfpr.birdwatchapp.pattern.builder;

import br.edu.utfpr.birdwatchapp.request.BirdRequest;

public class BirdRequestBuilder {

  private String specie;
  private String color;
  private String commonName;

  public BirdRequestBuilder setSpecie(String specie) {
    this.specie = specie;
    return this;
  }

  public BirdRequestBuilder setColor(String color) {
    this.color = color;
    return this;
  }

  public BirdRequestBuilder setCommonName(String commonName) {
    this.commonName = commonName;
    return this;
  }

  public BirdRequest build() {
    BirdRequest request = new BirdRequest();
    request.setSpecie(specie);
    request.setColor(color);
    request.setCommonName(commonName);
    return request;
  }
}