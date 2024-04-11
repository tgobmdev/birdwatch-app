package br.edu.utfpr.birdwatchapp.pattern.strategy.action;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationCreateActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.NavigationActionStrategy;

public class ObservationNavigationActionStrategy implements NavigationActionStrategy {

  private final AppCompatActivity activity;

  public ObservationNavigationActionStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public void execute() {
    Intent intent = new Intent(activity, ObservationCreateActivity.class);
    activity.startActivity(intent);
  }
}