package br.edu.utfpr.birdwatchapp.ui.modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.BirdEntity;
import br.edu.utfpr.birdwatchapp.util.ConstantsUtil;

public class BirdModal {

  private final Context context;
  private final BirdEntity bird;
  private final OnSaveListener onSaveListener;

  public BirdModal(Context context, BirdEntity bird, OnSaveListener onSaveListener) {
    this.context = context;
    this.bird = bird;
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
        .setTitle(R.string.label_edit_bird);
  }

  private View createFormView() {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup root = null;

    if (context instanceof Activity) {
      root = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
    }

    View formView = inflater.inflate(R.layout.activity_bird_form, root, false);
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
    EditText textEditSpecie = formView.findViewById(R.id.activity_bird_form_field_specie);
    EditText editTextColor = formView.findViewById(R.id.activity_bird_form_field_color);
    EditText textEditCommonName = formView.findViewById(R.id.activity_bird_form_field_common_name);

    initializeFormFields(textEditSpecie, editTextColor, textEditCommonName);
  }

  private void initializeFormFields(EditText textEditViewSpecie, EditText editTextViewColor,
      EditText textEditViewCommonName) {
    textEditViewSpecie.setText(bird.getSpecie());
    editTextViewColor.setText(bird.getColor());
    textEditViewCommonName.setText(bird.getCommonName());
  }

  private void setupDialogButtons(AlertDialog.Builder builder, View formView) {
    builder.setPositiveButton(R.string.save, (dialog, which) -> saveBird(formView));
    builder.setNegativeButton(R.string.cancel, null);
  }

  private void saveBird(View formView) {
    EditText textEditSpecie = formView.findViewById(R.id.activity_bird_form_field_specie);
    EditText editTextColor = formView.findViewById(R.id.activity_bird_form_field_color);
    EditText textEditCommonName = formView.findViewById(R.id.activity_bird_form_field_common_name);

    bird.setSpecie(textEditSpecie.getText().toString());
    bird.setColor(editTextColor.getText().toString());
    bird.setCommonName(textEditCommonName.getText().toString());

    if (onSaveListener != null) {
      onSaveListener.onSave(bird);
    }
  }

  public interface OnSaveListener {

    void onSave(BirdEntity bird);
  }
}