package br.edu.utfpr.birdwatchapp.pattern.strategy.executor;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationListActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;

public class ObservationExecutorStrategy implements ExecutorStrategy {

  private final AppCompatActivity activity;

  public ObservationExecutorStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public boolean execute() {
    try {
      Intent intent = new Intent(activity, ObservationListActivity.class);
      activity.startActivity(intent);
      return true;
    } catch (ActivityNotFoundException e) {
      return false;
    }
  }
}