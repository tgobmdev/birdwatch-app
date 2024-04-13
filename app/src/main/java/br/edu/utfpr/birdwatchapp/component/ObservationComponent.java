package br.edu.utfpr.birdwatchapp.component;

import android.content.Context;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.persistence.Database;
import java.util.List;

public class ObservationComponent {

  private final Database database;

  public ObservationComponent(Context context) {
    this.database = Database.getDatabase(context);
  }

  public List<ObservationEntity> findAllObservations() {
    return database.observationDao().findAll();
  }

  public ObservationEntity findObservationById(Long id) {
    return database.observationDao().findById(id);
  }

  public void saveObservation(ObservationEntity observation) {
    database.observationDao().save(observation);
  }

  public void updateObservation(ObservationEntity observation) {
    database.observationDao().update(observation);
  }

  public void deleteObservation(ObservationEntity observation) {
    database.observationDao().delete(observation);
  }
}