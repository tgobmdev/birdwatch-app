package br.edu.utfpr.birdwatchapp.parse;

import android.content.res.Resources;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class ObservationParse {

  private final String[] dates;
  private final String[] locations;
  private final String[] species;

  public ObservationParse(final Resources resources) {
    this.dates = resources.getStringArray(R.array.observation_dates);
    this.locations = resources.getStringArray(R.array.observation_locations);
    this.species = resources.getStringArray(R.array.observation_species);
  }

  public ObservationEntity toObservationEntity(int position) {
    ObservationEntity observation = new ObservationEntity();
    observation.setId(position + 1);
    observation.setDateTime(DateUtil.parseDate(dates[position]));
    observation.setLocation(locations[position]);
    observation.setSpecies(species[position]);
    return observation;
  }

  public List<ObservationEntity> toObservationsList() {
    List<ObservationEntity> observations = new ArrayList<>();
    for (int i = 0; i < dates.length; i++) {
      observations.add(toObservationEntity(i));
    }
    return observations;
  }
}