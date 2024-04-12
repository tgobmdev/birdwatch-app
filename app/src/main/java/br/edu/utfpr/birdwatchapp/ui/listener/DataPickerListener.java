package br.edu.utfpr.birdwatchapp.ui.listener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Locale;

public interface DataPickerListener {

  default void setDataPickerListener(Context context, EditText field) {
    field.setOnClickListener(v -> openDatePicker(context, field));
  }

  default void openDatePicker(Context context, EditText field) {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
        (view, selectedYear, selectedMonth, selectedDay) -> {
          String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear,
              selectedMonth + 1, selectedDay);
          field.setText(selectedDate);
        }, year, month, day);
    datePickerDialog.show();
  }
}