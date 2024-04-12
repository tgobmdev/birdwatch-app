package br.edu.utfpr.birdwatchapp.pattern.strategy.executor;

import android.content.ActivityNotFoundException;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;

public class FinishExecutorStrategy implements ExecutorStrategy {

  private final AppCompatActivity activity;

  public FinishExecutorStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public boolean execute() {
    try {
      activity.finish();
      return true;
    } catch (ActivityNotFoundException e) {
      return false;
    }
  }
}