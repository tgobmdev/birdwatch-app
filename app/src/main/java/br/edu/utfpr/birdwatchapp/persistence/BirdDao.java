package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import java.util.List;

@Dao
public interface BirdDao {

  @Query("SELECT * FROM birds ORDER BY id ASC")
  List<BirdEntity> findAll();

  @Query("SELECT * FROM birds WHERE specie = :specie")
  BirdEntity findBySpecie(String specie);

  @Insert
  void save(BirdEntity birdEntity);
}