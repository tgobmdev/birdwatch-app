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
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
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
import java.util.ArrayList;
import java.util.List;

public class ObservationCreateActivity extends AppCompatActivity implements ActionBarConfig,
    KeyboardConfig, DataPickerListener, TimePickerListener {

  private EditText editTextDate, editTextTime, editTextLocation;
  private Spinner spinnerSpecie;
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
    editTextDate = findViewById(R.id.editTextDate);
    editTextTime = findViewById(R.id.editTextTime);
    editTextLocation = findViewById(R.id.editTextLocation);
    spinnerSpecie = findViewById(R.id.spinnerSpecie);

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
    setDataPickerListener(this, editTextDate);
    setTimePickerListener(this, editTextTime);
  }

  private void setupSpeciesSpinner() {
    List<BirdEntity> birdEntities = birdComponent.findAllBirds();

    if (birdEntities == null || birdEntities.isEmpty()) {
      new AlertDialog.Builder(this).setTitle(R.string.notice)
          .setMessage(R.string.warn_species_not_found)
          .setPositiveButton(R.string.ok, (dialog, which) -> finish()).show();
      return;
    }

    List<String> species = new ArrayList<>(birdEntities.size());
    for (BirdEntity bird : birdEntities) {
      species.add(bird.getSpecie());
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
        species);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerSpecie.setAdapter(adapter);
  }

  private void saveObservation() {
    ObservationValidator observationValidator = new ObservationValidator(this);
    boolean isValid = observationValidator.validateAllFields(editTextDate, editTextTime,
        editTextLocation, spinnerSpecie);

    if (!isValid) {
      return;
    }

    String date = editTextDate.getText().toString();
    String time = editTextTime.getText().toString();
    String location = editTextLocation.getText().toString();
    String specie = spinnerSpecie.getSelectedItem().toString();

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
    editTextDate.setText("");
    editTextTime.setText("");
    editTextLocation.setText("");
    spinnerSpecie.setSelection(0);
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