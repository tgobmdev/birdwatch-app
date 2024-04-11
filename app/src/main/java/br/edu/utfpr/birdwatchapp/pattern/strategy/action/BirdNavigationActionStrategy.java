package br.edu.utfpr.birdwatchapp.pattern.strategy.action;

import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.NavigationActionStrategy;

public class BirdNavigationActionStrategy implements NavigationActionStrategy {

  private final AppCompatActivity activity;

  public BirdNavigationActionStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public void execute() {
  }
}