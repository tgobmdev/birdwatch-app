package br.edu.utfpr.birdwatchapp.component;

import android.content.Context;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.persistence.Database;
import java.util.List;

public class BirdComponent {

  private final Database database;

  public BirdComponent(Context context) {
    this.database = Database.getDatabase(context);
  }

  public List<BirdEntity> findAllBirds() {
    return database.birdDao().findAll();
  }

  public BirdEntity findBirdById(Long id) {
    return database.birdDao().findById(id);
  }

  public void saveBird(BirdEntity birdEntity) {
    database.birdDao().save(birdEntity);
  }

  public void deleteBird(BirdEntity birdEntity) {
    database.birdDao().delete(birdEntity);
  }
}