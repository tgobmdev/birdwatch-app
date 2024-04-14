package br.edu.utfpr.birdwatchapp.response;

public class ObservationResponse {

  private Long id;
  private String dateTime;
  private String location;
  private String specie;

  public ObservationResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
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