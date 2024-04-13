package br.edu.utfpr.birdwatchapp.persistence;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import java.util.Objects;

@Database(entities = {BirdEntity.class}, version = 1, exportSchema = false)
public abstract class BirdDatabase extends RoomDatabase {

  private static BirdDatabase instance;

  public static BirdDatabase getBirdDatabase(final Context context) {
    if (Objects.isNull(instance)) {
      synchronized (BirdDatabase.class) {
        if (Objects.isNull(instance)) {
          instance = Room.databaseBuilder(context, BirdDatabase.class, "birdwatch") //
              .allowMainThreadQueries() //
              .build();
        }
      }
    }
    return instance;
  }

  public abstract BirdDao birdDao();
}