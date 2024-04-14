package br.edu.utfpr.birdwatchapp.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "observations", foreignKeys = @ForeignKey(entity = BirdEntity.class, parentColumns = "id", childColumns = "birdId", onDelete = ForeignKey.CASCADE))
public class ObservationEntity implements Serializable {

  @PrimaryKey(autoGenerate = true)
  private Long id;
  private Date dateTime;
  private String location;

  private Long birdId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Long getBirdId() {
    return birdId;
  }

  public void setBirdId(Long birdId) {
    this.birdId = birdId;
  }
}