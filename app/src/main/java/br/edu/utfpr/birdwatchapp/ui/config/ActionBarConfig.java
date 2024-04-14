package br.edu.utfpr.birdwatchapp.ui.config;

import androidx.appcompat.app.ActionBar;

public interface ActionBarConfig {

  default void enableHomeAsUp() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  ActionBar getSupportActionBar();
}