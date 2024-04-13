package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;

@Dao
public interface BirdDao {

  @Insert
  void save(BirdEntity birdEntity);
}