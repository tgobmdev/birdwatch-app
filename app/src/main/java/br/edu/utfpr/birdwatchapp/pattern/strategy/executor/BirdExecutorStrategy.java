package br.edu.utfpr.birdwatchapp.pattern.strategy.executor;

import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.ExecutorStrategy;

public class BirdExecutorStrategy implements ExecutorStrategy {

  private final AppCompatActivity activity;

  public BirdExecutorStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public boolean execute() {
    return true;
  }
}