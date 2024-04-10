package br.edu.utfpr.birdwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationCreateActivity;
import br.edu.utfpr.birdwatchapp.activity.ObservationListActivity;

public class MainActivity extends AppCompatActivity {

  private Button buttonCreateObservation, buttonViewObservations;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    buttonCreateObservation = findViewById(R.id.buttonCreateObservation);
    buttonViewObservations = findViewById(R.id.buttonViewObservations);

    setup();
  }

  private void setup() {
    setupButtonCreateObservation();
    setupButtonViewObservations();
  }

  private void setupButtonCreateObservation() {
    buttonCreateObservation.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, ObservationCreateActivity.class);
      startActivity(intent);
    });
  }

  private void setupButtonViewObservations() {
    buttonViewObservations.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, ObservationListActivity.class);
      startActivity(intent);
    });
  }
}