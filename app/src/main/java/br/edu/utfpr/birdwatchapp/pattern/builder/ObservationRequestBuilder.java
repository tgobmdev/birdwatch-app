package br.edu.utfpr.birdwatchapp.pattern.builder;

import br.edu.utfpr.birdwatchapp.request.ObservationRequest;
import java.util.Date;

public class ObservationRequestBuilder {

  private Date dateTime;
  private String location;
  private String specie;

  public ObservationRequestBuilder setDateTime(Date dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public ObservationRequestBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public ObservationRequestBuilder setSpecie(String specie) {
    this.specie = specie;
    return this;
  }

  public ObservationRequest build() {
    return new ObservationRequest(dateTime, location, specie);
  }
}