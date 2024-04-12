package br.edu.utfpr.birdwatchapp.component;

import android.content.Context;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.persistence.ObservationDatabase;
import java.util.List;

public class ObservationComponent {

  private final ObservationDatabase observationDatabase;

  public ObservationComponent(Context context) {
    this.observationDatabase = ObservationDatabase.getObservationDatabase(context);
  }

  public List<ObservationEntity> findAllObservations() {
    return observationDatabase.observationDao().findAll();
  }

  public ObservationEntity findObservationById(Long id) {
    return observationDatabase.observationDao().findById(id);
  }

  public void saveObservation(ObservationEntity observation) {
    observationDatabase.observationDao().save(observation);
  }

  public void deleteObservation(ObservationEntity observation) {
    observationDatabase.observationDao().delete(observation);
  }
}