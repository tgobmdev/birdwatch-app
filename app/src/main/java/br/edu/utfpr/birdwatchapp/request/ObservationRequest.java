package br.edu.utfpr.birdwatchapp.request;

import java.util.Date;

public class ObservationRequest {

  private Date dateTime;
  private String location;
  private String specie;

  public ObservationRequest() {
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

  public String getSpecie() {
    return specie;
  }

  public void setSpecie(String specie) {
    this.specie = specie;
  }
}