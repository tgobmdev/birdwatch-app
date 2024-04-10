package br.edu.utfpr.birdwatchapp;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ObservationCreateActivity extends AppCompatActivity {

  private EditText editTextDate, editTextTime, editTextLocation;
  private RadioGroup radioGroupSpecies;
  private CheckBox checkBoxObservationType;
  private Spinner spinnerBirdSpecies;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_create);

    editTextDate = findViewById(R.id.editTextDate);
    editTextTime = findViewById(R.id.editTextTime);
    editTextLocation = findViewById(R.id.editTextLocation);
    radioGroupSpecies = findViewById(R.id.radioGroupSpecies);
    checkBoxObservationType = findViewById(R.id.checkBoxObservationType);
    spinnerBirdSpecies = findViewById(R.id.spinnerBirdSpecies);

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
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bird_species,
        android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerBirdSpecies.setAdapter(adapter);
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
    RadioButton radioButton = findViewById(radioGroupSpecies.getCheckedRadioButtonId());
    String species = radioButton.getText().toString();
    boolean observationConfirmed = checkBoxObservationType.isChecked();
    String specie = spinnerBirdSpecies.getSelectedItem().toString();

    Toast.makeText(this, "Observation saved successfully!", Toast.LENGTH_SHORT).show();
  }

  private void clearForm() {
    editTextDate.setText("");
    editTextTime.setText("");
    editTextLocation.setText("");
    radioGroupSpecies.clearCheck();
    checkBoxObservationType.setChecked(false);
    spinnerBirdSpecies.setSelection(0);
    Toast.makeText(this, "Form cleared!", Toast.LENGTH_SHORT).show();
  }
}