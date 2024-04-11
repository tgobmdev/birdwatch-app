package br.edu.utfpr.birdwatchapp.request;

import java.util.Date;

public class ObservationRequest {

  private Date dateTime;
  private String location;
  private String species;

  public ObservationRequest() {
  }

  public ObservationRequest(Date dateTime, String location, String species) {
    this.dateTime = dateTime;
    this.location = location;
    this.species = species;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }
}