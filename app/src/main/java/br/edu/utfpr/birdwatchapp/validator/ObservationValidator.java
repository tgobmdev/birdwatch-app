package br.edu.utfpr.birdwatchapp.validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.edu.utfpr.birdwatchapp.R;

public class ObservationValidator {

  private final Context context;

  public ObservationValidator(Context context) {
    this.context = context;
  }

  private void setError(View view, String errorMessage) {
    if (view instanceof EditText) {
      ((EditText) view).setError(errorMessage);
    } else if (view instanceof TextView) {
      ((TextView) view).setError(errorMessage);
    }
  }

  public boolean validateDate(EditText dateField) {
    boolean isValid = !dateField.getText().toString().isEmpty();
    setError(dateField, isValid ? null : context.getString(R.string.error_date_required));
    return isValid;
  }

  public boolean validateTime(EditText timeField) {
    boolean isValid = !timeField.getText().toString().isEmpty();
    setError(timeField, isValid ? null : context.getString(R.string.error_time_required));
    return isValid;
  }

  public boolean validateLocation(EditText locationField) {
    boolean isValid = !locationField.getText().toString().isEmpty();
    setError(locationField, isValid ? null : context.getString(R.string.error_location_required));
    return isValid;
  }

  public boolean validateSpecie(Spinner specieSpinner) {
    String specie = (String) specieSpinner.getSelectedItem();
    View selectedView = specieSpinner.getSelectedView();
    boolean isValid = specie != null && !specie.isEmpty();
    setError(selectedView, isValid ? null : context.getString(R.string.error_specie_required));
    return isValid;
  }

  public boolean validateAllFields(EditText dateField, EditText timeField, EditText locationField,
      Spinner specieSpinner) {
    boolean isDateValid = validateDate(dateField);
    boolean isTimeValid = validateTime(timeField);
    boolean isLocationValid = validateLocation(locationField);
    boolean isSpecieValid = validateSpecie(specieSpinner);
    return isDateValid && isTimeValid && isLocationValid && isSpecieValid;
  }
}