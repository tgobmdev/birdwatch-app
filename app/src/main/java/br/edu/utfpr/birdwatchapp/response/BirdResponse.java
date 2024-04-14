package br.edu.utfpr.birdwatchapp.response;

public class BirdResponse {

  private Long id;
  private String specie;
  private String color;
  private String commonName;

  public BirdResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSpecie() {
    return specie;
  }

  public void setSpecie(String specie) {
    this.specie = specie;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }
}