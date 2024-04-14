package br.edu.utfpr.birdwatchapp.pattern.strategy.executor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import br.edu.utfpr.birdwatchapp.activity.ObservationCreateActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;

public class ObservationCreateExecutorStrategy implements ExecutorStrategy {

  private final Context context;
  private final ActivityResultLauncher<Intent> activityResultLauncher;

  public ObservationCreateExecutorStrategy(Context context,
      ActivityResultLauncher<Intent> activityResultLauncher) {
    this.context = context;
    this.activityResultLauncher = activityResultLauncher;
  }

  @Override
  public boolean execute() {
    try {
      Intent intent = new Intent(context, ObservationCreateActivity.class);
      activityResultLauncher.launch(intent);
      return true;
    } catch (ActivityNotFoundException | SecurityException e) {
      return false;
    }
  }
}