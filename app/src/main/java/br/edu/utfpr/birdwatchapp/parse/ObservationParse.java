package br.edu.utfpr.birdwatchapp.parse;

import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.pattern.builder.ObservationResponseBuilder;
import br.edu.utfpr.birdwatchapp.request.ObservationRequest;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import java.util.ArrayList;
import java.util.List;

public final class ObservationParse {

  public ObservationParse() {
  }

  public ObservationEntity toObservationEntity(ObservationRequest observationRequest) {
    ObservationEntity observation = new ObservationEntity();
    observation.setDateTime(observationRequest.getDateTime());
    observation.setLocation(observationRequest.getLocation());
    observation.setSpecie(observationRequest.getSpecie());
    return observation;
  }

  public ObservationResponse toObservationResponse(ObservationEntity observationEntity) {
    return new ObservationResponseBuilder() //
        .setId(observationEntity.getId()) //
        .setDateTime(observationEntity.getDateTime()) //
        .setLocation(observationEntity.getLocation()) //
        .setSpecie(observationEntity.getSpecie()) //
        .build();
  }

  public List<ObservationResponse> toResponseList(List<ObservationEntity> observationEntities) {
    List<ObservationResponse> observationResponses = new ArrayList<>(observationEntities.size());
    for (ObservationEntity observationEntity : observationEntities) {
      ObservationResponse observationResponse = toObservationResponse(observationEntity);
      observationResponses.add(observationResponse);
    }
    return observationResponses;
  }
}