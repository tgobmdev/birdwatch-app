package br.edu.utfpr.birdwatchapp.persistence;

import androidx.room.Dao;
import androidx.room.Query;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import java.util.List;

@Dao
public interface ObservationResponseDao {

  @Query("SELECT o.id, "
      + "strftime('%Y-%m-%dT%H:%M', o.dateTime / 1000, 'unixepoch') as dateTime, "
      + "o.location, "
      + "b.specie "
      + "FROM observations "
      + "o JOIN birds b ON o.birdId = b.id")
  List<ObservationResponse> findAllObservationResponses();
}