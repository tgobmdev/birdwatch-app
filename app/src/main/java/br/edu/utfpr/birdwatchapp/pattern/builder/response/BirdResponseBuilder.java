package br.edu.utfpr.birdwatchapp.pattern.builder.response;

import br.edu.utfpr.birdwatchapp.response.BirdResponse;

public class BirdResponseBuilder {

  private Long id;
  private String specie;
  private String color;
  private String commonName;

  public BirdResponseBuilder setId(Long id) {
    this.id = id;
    return this;
  }

  public BirdResponseBuilder setSpecie(String specie) {
    this.specie = specie;
    return this;
  }

  public BirdResponseBuilder setColor(String color) {
    this.color = color;
    return this;
  }

  public BirdResponseBuilder setCommonName(String commonName) {
    this.commonName = commonName;
    return this;
  }

  public BirdResponse build() {
    BirdResponse response = new BirdResponse();
    response.setId(id);
    response.setSpecie(specie);
    response.setColor(color);
    response.setCommonName(commonName);
    return response;
  }
}