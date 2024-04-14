package br.edu.utfpr.birdwatchapp.validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.edu.utfpr.birdwatchapp.R;

public class BirdValidator {

  private final Context context;

  public BirdValidator(Context context) {
    this.context = context;
  }

  private void setError(View view, String errorMessage) {
    if (view instanceof EditText) {
      ((EditText) view).setError(errorMessage);
    } else if (view instanceof TextView) {
      ((TextView) view).setError(errorMessage);
    }
  }

  public boolean validateSpecie(EditText specieField) {
    boolean isValid = !specieField.getText().toString().isEmpty();
    setError(specieField, isValid ? null : context.getString(R.string.error_specie_required));
    return isValid;
  }

  public boolean validateColor(EditText colorField) {
    boolean isValid = !colorField.getText().toString().isEmpty();
    setError(colorField, isValid ? null : context.getString(R.string.error_color_required));
    return isValid;
  }

  public boolean validateCommonName(EditText commonNameField) {
    boolean isValid = !commonNameField.getText().toString().isEmpty();
    setError(commonNameField,
        isValid ? null : context.getString(R.string.error_common_name_required));
    return isValid;
  }

  public boolean validateAllFields(EditText specieField, EditText colorField,
      EditText commonNameField) {
    boolean isSpecieValid = validateSpecie(specieField);
    boolean isColorValid = validateColor(colorField);
    boolean isCommonNameValid = validateCommonName(commonNameField);
    return isSpecieValid && isColorValid && isCommonNameValid;
  }
}