package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.List;

@Dao
public interface ObservationDao {

  @Query("SELECT * FROM observations ORDER BY id ASC")
  List<ObservationEntity> findAll();

  @Query("SELECT * FROM observations WHERE id = :id")
  ObservationEntity findById(Long id);

  @Insert
  void save(ObservationEntity observationEntity);

  @Delete
  void delete(ObservationEntity observationEntity);
}