package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;

@Dao
public interface ObservationDao {

  @Query("SELECT * FROM observations WHERE id = :id")
  ObservationEntity findById(Long id);

  @Insert
  void save(ObservationEntity observationEntity);

  @Update
  void update(ObservationEntity observationEntity);

  @Delete
  void delete(ObservationEntity observationEntity);
}