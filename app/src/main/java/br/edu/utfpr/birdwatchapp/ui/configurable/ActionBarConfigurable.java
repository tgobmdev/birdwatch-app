package br.edu.utfpr.birdwatchapp.ui.configurable;

import androidx.appcompat.app.ActionBar;

public interface ActionBarConfigurable {

  default void enableHomeAsUp() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  ActionBar getSupportActionBar();
}