package br.edu.utfpr.birdwatchapp.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "observations")
public class ObservationEntity implements Serializable {

  @PrimaryKey(autoGenerate = true)
  private long id;
  private Date dateTime;
  private String location;
  private String species;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  @NonNull
  @Override
  public String toString() {
    return "id: " + id + ", dateTime: " + dateTime + ", location: " + location + ", species: "
        + species;
  }
}