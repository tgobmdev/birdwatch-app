package br.edu.utfpr.birdwatchapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import java.util.List;

@Entity(tableName = "birds")
public class BirdEntity {

  @PrimaryKey(autoGenerate = true)
  private long id;
  private String specie;
  private String color;
  private String commonName;

  @Relation(parentColumn = "id", entityColumn = "birdId")
  private List<ObservationEntity> observations;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public List<ObservationEntity> getObservations() {
    return observations;
  }

  public void setObservations(List<ObservationEntity> observations) {
    this.observations = observations;
  }
}