package br.edu.utfpr.birdwatchapp.parse;

import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.request.ObservationRequest;

public final class ObservationParse {

  public ObservationParse() {
  }

  public ObservationEntity toObservationEntity(ObservationRequest observationRequest) {
    ObservationEntity observation = new ObservationEntity();
    observation.setDateTime(observationRequest.getDateTime());
    observation.setLocation(observationRequest.getLocation());
    observation.setBirdId(observationRequest.getBirdId());
    return observation;
  }
}