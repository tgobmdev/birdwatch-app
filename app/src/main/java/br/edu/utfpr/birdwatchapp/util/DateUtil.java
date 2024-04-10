package br.edu.utfpr.birdwatchapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

  private static final String DATE_FORMAT_PATTERN = "yyyy–MM–dd'T'HH:mm";

  public static Date parseDate(String dateString) {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
    Date date;
    try {
      date = sdf.parse(dateString);
      return date;
    } catch (ParseException e) {
      date = null;
    }
    return date;
  }

  public static String formatDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
    return sdf.format(date);
  }
}