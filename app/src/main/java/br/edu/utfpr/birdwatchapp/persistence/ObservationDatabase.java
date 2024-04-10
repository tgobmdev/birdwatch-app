package br.edu.utfpr.birdwatchapp.persistence;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.edu.utfpr.birdwatchapp.converter.DateConverter;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.Objects;

@Database(entities = {ObservationEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ObservationDatabase extends RoomDatabase {

  private static ObservationDatabase instance;

  public static ObservationDatabase getObservationDatabase(final Context context) {
    if (Objects.isNull(instance)) {
      synchronized (ObservationDatabase.class) {
        if (Objects.isNull(instance)) {
          instance = Room.databaseBuilder(context, ObservationDatabase.class, "observations.db") //
              .allowMainThreadQueries() //
              .build();
        }
      }
    }
    return instance;
  }

  public abstract ObservationDao observationDao();
}