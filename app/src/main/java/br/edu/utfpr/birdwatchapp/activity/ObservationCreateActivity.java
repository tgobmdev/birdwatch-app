package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.util.DateUtil;
import br.edu.utfpr.birdwatchapp.util.MessageUtil;

public class ObservationCreateActivity extends AppCompatActivity {

  private EditText editTextDate, editTextTime, editTextLocation;
  private Spinner spinnerSpecies;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_create);

    editTextDate = findViewById(R.id.editTextDate);
    editTextTime = findViewById(R.id.editTextTime);
    editTextLocation = findViewById(R.id.editTextLocation);
    spinnerSpecies = findViewById(R.id.spinnerSpecies);

    setup();
  }

  private void setup() {
    setupSpinner();
    setupButtonSave();
    setupButtonClear();
    setupHideKeyboard();
  }

  private void setupButtonSave() {
    findViewById(R.id.buttonSave).setOnClickListener(v -> saveObservation());
  }

  private void setupButtonClear() {
    findViewById(R.id.buttonClear).setOnClickListener(v -> clearForm());
  }

  private void setupSpinner() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.observation_species, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerSpecies.setAdapter(adapter);
  }

  private void setupHideKeyboard() {
    findViewById(R.id.layoutObservation).setOnTouchListener((v, event) -> {
      hideKeyboard();
      v.performClick();
      return false;
    });
  }

  private void hideKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
        INPUT_METHOD_SERVICE);
    if (getCurrentFocus() != null) {
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  private void saveObservation() {
    String date = editTextDate.getText().toString();
    String time = editTextTime.getText().toString();
    String location = editTextLocation.getText().toString();
    String specie = spinnerSpecies.getSelectedItem().toString();

    ObservationEntity observation = new ObservationEntity();
    observation.setDateTime(DateUtil.parseDate(date + "T" + time));
    observation.setLocation(location);
    observation.setSpecies(specie);

    Intent intent = new Intent();
    intent.putExtra(MessageUtil.EXTRA_OBSERVATION, observation);
    setResult(Activity.RESULT_OK, intent);

    finish();
  }

  private void clearForm() {
    editTextDate.setText("");
    editTextTime.setText("");
    editTextLocation.setText("");
    spinnerSpecies.setSelection(0);
    Toast.makeText(this, "Form cleared!", Toast.LENGTH_SHORT).show();
  }

  public void saveObservation(View view) {
    saveObservation();
  }

  public void clearForm(View view) {
    clearForm();
  }
}