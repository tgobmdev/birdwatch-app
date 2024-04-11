package br.edu.utfpr.birdwatchapp.pattern.strategy.action;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.activity.AboutActivity;
import br.edu.utfpr.birdwatchapp.pattern.strategy.NavigationActionStrategy;

public class AboutNavigationActionStrategy implements NavigationActionStrategy {

  private final AppCompatActivity activity;

  public AboutNavigationActionStrategy(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Override
  public void execute() {
    Intent intent = new Intent(activity, AboutActivity.class);
    activity.startActivity(intent);
  }
}