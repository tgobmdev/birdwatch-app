package br.edu.utfpr.birdwatchapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

  public static Date parseDate(String dateString, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
    Date date;
    try {
      date = sdf.parse(dateString);
      return date;
    } catch (ParseException e) {
      date = null;
    }
    return date;
  }

  public static Date parseDateDefault(String dateString) {
    return parseDate(dateString, ConstantsUtil.DATE_FORMAT_DEFAULT);
  }

  public static String formatDate(Date date, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
    return sdf.format(date);
  }
}