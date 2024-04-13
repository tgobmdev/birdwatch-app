package br.edu.utfpr.birdwatchapp.parse;

import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.pattern.builder.BirdResponseBuilder;
import br.edu.utfpr.birdwatchapp.request.BirdRequest;
import br.edu.utfpr.birdwatchapp.response.BirdResponse;
import java.util.ArrayList;
import java.util.List;

public final class BirdParse {

  public BirdParse() {
  }

  public BirdEntity toBirdEntity(BirdRequest birdRequest) {
    BirdEntity bird = new BirdEntity();
    bird.setSpecie(birdRequest.getSpecie());
    bird.setColor(birdRequest.getColor());
    bird.setCommonName(birdRequest.getCommonName());
    return bird;
  }

  public BirdResponse toBirdResponse(BirdEntity birdEntity) {
    return new BirdResponseBuilder() //
        .setId(birdEntity.getId()) //
        .setSpecie(birdEntity.getSpecie()) //
        .setColor(birdEntity.getColor()) //
        .setCommonName(birdEntity.getCommonName()) //
        .build();
  }

  public List<BirdResponse> toResponseList(List<BirdEntity> birdEntities) {
    List<BirdResponse> birdResponses = new ArrayList<>(birdEntities.size());
    for (BirdEntity birdEntity : birdEntities) {
      BirdResponse birdResponse = toBirdResponse(birdEntity);
      birdResponses.add(birdResponse);
    }
    return birdResponses;
  }
}