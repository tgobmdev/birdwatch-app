package br.edu.utfpr.birdwatchapp.observable;

import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.ArrayList;
import java.util.List;

public class ObservationObservable {

  private static ObservationObservable instance;
  private final List<ObservationEntity> observations;

  private ObservationObservable() {
    observations = new ArrayList<>();
  }

  public static synchronized ObservationObservable getInstance() {
    if (instance == null) {
      instance = new ObservationObservable();
    }
    return instance;
  }

  public List<ObservationEntity> getObservations() {
    return observations;
  }

  public void addObservation(ObservationEntity observation) {
    observations.add(observation);
  }
}