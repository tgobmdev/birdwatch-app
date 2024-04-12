package br.edu.utfpr.birdwatchapp.ui.config;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

public interface KeyboardConfig {

  default void hideKeyboard(View view) {
    view.setOnTouchListener((v, event) -> {
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
          INPUT_METHOD_SERVICE);
      if (getCurrentFocus() != null) {
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
      }
      v.performClick();
      return false;
    });
  }

  Object getSystemService(String name);

  View getCurrentFocus();
}