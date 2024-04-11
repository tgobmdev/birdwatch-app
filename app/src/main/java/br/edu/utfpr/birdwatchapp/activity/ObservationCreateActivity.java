package br.edu.utfpr.birdwatchapp.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.parse.ObservationParse;
import br.edu.utfpr.birdwatchapp.pattern.builder.ObservationRequestBuilder;
import br.edu.utfpr.birdwatchapp.persistence.ObservationDatabase;
import br.edu.utfpr.birdwatchapp.request.ObservationRequest;
import br.edu.utfpr.birdwatchapp.ui.configurable.ActionBarConfigurable;
import br.edu.utfpr.birdwatchapp.ui.configurable.KeyboardConfigurable;
import br.edu.utfpr.birdwatchapp.util.DateUtil;
import java.util.Calendar;
import java.util.Locale;

public class ObservationCreateActivity extends AppCompatActivity implements ActionBarConfigurable,
    KeyboardConfigurable {

  private EditText editTextDate, editTextTime, editTextLocation;
  private Spinner spinnerSpecie;
  private ObservationParse observationParse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observation_create);

    ConstraintLayout layoutObservation = findViewById(R.id.layoutObservation);
    editTextDate = findViewById(R.id.editTextDate);
    editTextTime = findViewById(R.id.editTextTime);
    editTextLocation = findViewById(R.id.editTextLocation);
    spinnerSpecie = findViewById(R.id.spinnerSpecie);
    observationParse = new ObservationParse();

    enableHomeAsUp();
    setupSpinner();
    hideKeyboard(layoutObservation);
    editTextDate.setOnClickListener(v -> openDatePicker());
    editTextTime.setOnClickListener(v -> openTimePicker());
  }

  private void setupSpinner() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.observation_species, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerSpecie.setAdapter(adapter);
  }

  private void openDatePicker() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
        (view, selectedYear, selectedMonth, selectedDay) -> {
          String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear,
              selectedMonth + 1, selectedDay);
          editTextDate.setText(selectedDate);
        }, year, month, day);
    datePickerDialog.show();
  }

  private void openTimePicker() {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
        (view, selectedHour, selectedMinute) -> {
          String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour,
              selectedMinute);
          editTextTime.setText(selectedTime);
        }, hour, minute, true);
    timePickerDialog.show();
  }

  private void saveObservation() {
    String date = editTextDate.getText().toString();
    String time = editTextTime.getText().toString();
    String location = editTextLocation.getText().toString();
    String specie = spinnerSpecie.getSelectedItem().toString();

    ObservationRequest observationRequest = new ObservationRequestBuilder() //
        .setDateTime(DateUtil.parseDate(date + "T" + time)) //
        .setLocation(location).setSpecie(specie) //
        .build();

    ObservationEntity observation = observationParse.toObservationEntity(observationRequest);
    ObservationDatabase.getObservationDatabase(this).observationDao().save(observation);
  }

  private void clearForm() {
    editTextDate.setText("");
    editTextTime.setText("");
    editTextLocation.setText("");
    spinnerSpecie.setSelection(0);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_observation_create, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int idItem = item.getItemId();
    if (idItem == R.id.menu_observation_save) {
      saveObservation();
      return true;
    } else {
      if (idItem == R.id.menu_observation_clear) {
        clearForm();
        return true;
      } else if (item.getItemId() == android.R.id.home) {
        finish();
        return true;
      } else {
        return super.onOptionsItemSelected(item);
      }
    }
  }
}