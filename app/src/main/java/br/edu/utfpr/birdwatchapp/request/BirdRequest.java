package br.edu.utfpr.birdwatchapp.request;

public class BirdRequest {

  private String specie;
  private String color;
  private String commonName;

  public BirdRequest() {
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