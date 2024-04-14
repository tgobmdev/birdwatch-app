package br.edu.utfpr.birdwatchapp.persistence;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.edu.utfpr.birdwatchapp.converter.DateConverter;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.Objects;

@androidx.room.Database(entities = {ObservationEntity.class,
    BirdEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class Database extends RoomDatabase {

  private static Database instance;

  public static Database getDatabase(final Context context) {
    if (Objects.isNull(instance)) {
      context.deleteDatabase("bird.db");
      synchronized (Database.class) {
        if (Objects.isNull(instance)) {
          instance = Room.databaseBuilder(context, Database.class, "bird.db") //
              .allowMainThreadQueries() //
              .build();
        }
      }
    }
    return instance;
  }

  public abstract ObservationDao observationDao();

  public abstract BirdDao birdDao();

  public abstract ObservationResponseDao observationResponseDao();
}