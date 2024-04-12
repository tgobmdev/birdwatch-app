package br.edu.utfpr.birdwatchapp.ui.modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import br.edu.utfpr.birdwatchapp.util.ConstantsUtil;
import br.edu.utfpr.birdwatchapp.util.DateUtil;

public class ObservationModal {

  private final Context context;
  private final ObservationEntity observation;
  private final OnSaveListener onSaveListener;

  public ObservationModal(Context context, ObservationEntity observation,
      OnSaveListener onSaveListener) {
    this.context = context;
    this.observation = observation;
    this.onSaveListener = onSaveListener;
  }

  public void show() {
    AlertDialog.Builder dialogBuilder = createAlertDialog();
    View formView = createFormView();
    setupFormFields(formView);
    dialogBuilder.setView(formView);
    setupDialogButtons(dialogBuilder, formView);
    dialogBuilder.create().show();
  }

  private AlertDialog.Builder createAlertDialog() {
    return new AlertDialog.Builder(context) //
        .setTitle(R.string.label_edit_observation);
  }

  private View createFormView() {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup root = null;

    if (context instanceof Activity) {
      root = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
    }

    View formView = inflater.inflate(R.layout.activity_observation_form, root, false);
    applyFormPadding(formView);
    return formView;
  }

  private void applyFormPadding(View formView) {
    int paddingBottomPx = convertDpToPx();
    formView.setPadding(ConstantsUtil.ZERO, ConstantsUtil.ZERO, ConstantsUtil.ZERO,
        paddingBottomPx);
  }

  private int convertDpToPx() {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round(ConstantsUtil.TWENTY_EIGHT * density);
  }

  private void setupFormFields(View formView) {
    EditText editTextDate = formView.findViewById(R.id.editTextDate);
    EditText editTextTime = formView.findViewById(R.id.editTextTime);
    EditText editTextLocation = formView.findViewById(R.id.editTextLocation);
    Spinner spinnerSpecie = formView.findViewById(R.id.spinnerSpecie);

    initializeFormFields(editTextDate, editTextTime, editTextLocation, spinnerSpecie);
  }

  private void initializeFormFields(EditText editTextDate, EditText editTextTime,
      EditText editTextLocation, Spinner spinnerSpecie) {
    String formattedDate = DateUtil.formatDate(observation.getDateTime(),
        ConstantsUtil.DATE_FORMAT_YYYY_MM_DD);
    String formattedTime = DateUtil.formatDate(observation.getDateTime(),
        ConstantsUtil.TIME_FORMAT_HH_MM);

    editTextDate.setText(formattedDate);
    editTextTime.setText(formattedTime);
    editTextLocation.setText(observation.getLocation());

    configureSpecieSpinner(spinnerSpecie);
  }

  private void configureSpecieSpinner(Spinner spinnerSpecie) {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
        R.array.observation_species, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerSpecie.setAdapter(adapter);

    if (observation.getSpecie() != null) {
      int position = adapter.getPosition(observation.getSpecie());
      if (position >= ConstantsUtil.ZERO) {
        spinnerSpecie.setSelection(position);
      }
    }
  }

  private void setupDialogButtons(AlertDialog.Builder builder, View formView) {
    builder.setPositiveButton(R.string.save, (dialog, which) -> saveObservation(formView));
    builder.setNegativeButton(R.string.cancel, null);
  }

  private void saveObservation(View formView) {
    EditText editTextDate = formView.findViewById(R.id.editTextDate);
    EditText editTextTime = formView.findViewById(R.id.editTextTime);
    EditText editTextLocation = formView.findViewById(R.id.editTextLocation);
    Spinner spinnerSpecie = formView.findViewById(R.id.spinnerSpecie);

    String dateTime = editTextDate.getText().toString() + "T" + editTextTime.getText().toString();
    observation.setDateTime(DateUtil.parseDateDefault(dateTime));
    observation.setLocation(editTextLocation.getText().toString());
    observation.setSpecie(spinnerSpecie.getSelectedItem().toString());

    if (onSaveListener != null) {
      onSaveListener.onSave(observation);
    }
  }

  public interface OnSaveListener {

    void onSave(ObservationEntity observation);
  }
}