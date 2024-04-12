package br.edu.utfpr.birdwatchapp.pattern.builder;

import br.edu.utfpr.birdwatchapp.response.ObservationResponse;

public class ObservationResponseBuilder {

  private Long id;
  private String dateTime;
  private String location;
  private String specie;

  public ObservationResponseBuilder setId(Long id) {
    this.id = id;
    return this;
  }

  public ObservationResponseBuilder setDateTime(String dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public ObservationResponseBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public ObservationResponseBuilder setSpecie(String specie) {
    this.specie = specie;
    return this;
  }

  public ObservationResponse build() {
    ObservationResponse response = new ObservationResponse();
    response.setId(id);
    response.setDateTime(dateTime);
    response.setLocation(location);
    response.setSpecie(specie);
    return response;
  }
}