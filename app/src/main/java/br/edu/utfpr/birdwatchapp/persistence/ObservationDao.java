package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.List;

@Dao
public interface ObservationDao {

  @Query("SELECT * FROM observations ORDER BY id ASC")
  List<ObservationEntity> findAll();

  @Insert
  long save(ObservationEntity observationEntity);
}