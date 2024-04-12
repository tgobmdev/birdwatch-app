package br.edu.utfpr.birdwatchapp.ui.listener;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TimePickerListener {

  default void setTimePickerListener(Context context, EditText field) {
    field.setOnClickListener(v -> openTimePicker(context, field));
  }

  default void openTimePicker(Context context, EditText field) {
    Calendar calendar = Calendar.getInstance();
    String currentTimeString = field.getText().toString();

    if (!currentTimeString.isEmpty()) {
      Pattern timePattern = Pattern.compile("^(\\d{2}):(\\d{2})$");
      Matcher matcher = timePattern.matcher(currentTimeString);

      if (matcher.matches()) {
        int hour = Integer.parseInt(Objects.requireNonNull(matcher.group(1)));
        int minute = Integer.parseInt(Objects.requireNonNull(matcher.group(2)));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
      }
    }

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