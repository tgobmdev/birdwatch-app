package br.edu.utfpr.birdwatchapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.component.BirdComponent;
import br.edu.utfpr.birdwatchapp.component.ObservationComponent;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import br.edu.utfpr.birdwatchapp.pattern.builder.ObservationRequestBuilder;
import br.edu.utfpr.birdwatchapp.request.ObservationRequest;
import br.edu.utfpr.birdwatchapp.ui.config.ActionBarConfig;
import br.edu.utfpr.birdwatchapp.ui.config.KeyboardConfig;
import br.edu.utfpr.birdwatchapp.ui.listener.DataPickerListener;
import br.edu.utfpr.birdwatchapp.ui.listener.TimePickerListener;
import br.edu.utfpr.birdwatchapp.util.DateUtil;
import br.edu.utfpr.birdwatchapp.validator.ObservationValidator;
import java.util.List;

public class ObservationCreateActivity extends AppCompatActivity implements ActionBarConfig,
    KeyboardConfig, DataPickerListener, TimePickerListener {

  private EditText formDate, formTime, formLocation;
  private Spinner formSpecie;

  private ObservationParse observationParse;

  private ObservationComponent observationComponent;
  private BirdComponent birdComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_form);
    setTitle(R.string.label_observations);

    initializeComponents();
    configureActionBar();
    configureListeners();
  }

  private void initializeComponents() {
    ConstraintLayout layoutObservation = findViewById(R.id.layoutObservation);
    formDate = findViewById(R.id.activity_observation_form_field_date);
    formTime = findViewById(R.id.activity_observation_form_field_time);
    formLocation = findViewById(R.id.activity_observation_form_field_location);
    formSpecie = findViewById(R.id.activity_observation_form_field_specie);

    observationParse = new ObservationParse();

    observationComponent = new ObservationComponent(this);
    birdComponent = new BirdComponent(this);

    setupSpeciesSpinner();
    hideKeyboard(layoutObservation);
  }

  private void configureActionBar() {
    enableHomeAsUp();
  }

  private void configureListeners() {
    setDataPickerListener(this, formDate);
    setTimePickerListener(this, formTime);
  }

  private void setupSpeciesSpinner() {
    List<String> species = birdComponent.findAllDistinctSpecies();

    if (species == null || species.isEmpty()) {
      new AlertDialog.Builder(this).setTitle(R.string.notice)
          .setMessage(R.string.warn_species_not_found)
          .setPositiveButton(R.string.ok, (dialog, which) -> finish()).show();
      return;
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
        species);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    formSpecie.setAdapter(adapter);
  }

  private void saveObservation() {
    ObservationValidator observationValidator = new ObservationValidator(this);
    boolean isValid = observationValidator.validateAllFields(formDate, formTime, formLocation,
        formSpecie);

    if (!isValid) {
      return;
    }

    String date = formDate.getText().toString();
    String time = formTime.getText().toString();
    String location = formLocation.getText().toString();
    String specie = formSpecie.getSelectedItem().toString();

    ObservationRequest observationRequest = new ObservationRequestBuilder().setDateTime(
            DateUtil.parseDateDefault(date + "T" + time)) //
        .setLocation(location) //
        .setSpecie(specie) //
        .build();

    ObservationEntity observationEntity = observationParse.toObservationEntity(observationRequest);
    observationComponent.saveObservation(observationEntity);

    Intent intent = new Intent();
    setResult(Activity.RESULT_OK, intent);
    finish();
  }

  private void clearForm() {
    formDate.setText("");
    formTime.setText("");
    formLocation.setText("");
    formSpecie.setSelection(0);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_save_clear, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    if (itemId == R.id.menu_save) {
      saveObservation();
      return true;
    } else if (itemId == R.id.menu_clear) {
      clearForm();
      return true;
    } else if (itemId == android.R.id.home) {
      finish();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }
}