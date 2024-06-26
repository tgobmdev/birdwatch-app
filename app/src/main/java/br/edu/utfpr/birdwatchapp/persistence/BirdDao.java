package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import java.util.List;

@Dao
public interface BirdDao {

  @Query("SELECT * FROM birds ORDER BY id ASC")
  List<BirdEntity> findAll();

  @Query("SELECT * FROM birds WHERE id = :id")
  BirdEntity findById(Long id);

  @Query("SELECT * FROM birds WHERE specie = :specie")
  BirdEntity findBirdBySpecie(String specie);

  @Query("SELECT DISTINCT specie FROM birds")
  List<String> findAllDistinctSpecies();

  @Insert
  void save(BirdEntity birdEntity);

  @Update
  void update(BirdEntity birdEntity);

  @Delete
  void delete(BirdEntity birdEntity);
}