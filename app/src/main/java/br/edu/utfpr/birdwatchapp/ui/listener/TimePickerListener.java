package br.edu.utfpr.birdwatchapp.ui.listener;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Locale;

public interface TimePickerListener {

  default void setTimePickerListener(Context context, EditText field) {
    field.setOnClickListener(v -> openTimePicker(context, field));
  }

  default void openTimePicker(Context context, EditText field) {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
        (view, selectedHour, selectedMinute) -> {
          String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour,
              selectedMinute);
          field.setText(selectedTime);
        }, hour, minute, true);
    timePickerDialog.show();
  }
}