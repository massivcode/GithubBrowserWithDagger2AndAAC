package com.massivcode.githubbrowserwithdagger2andaac.utils.time;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by massivcode@gmail.com on 2017-12-18.
 */

public class RemainsDateTimeFormatter {

  private static RemainsDateTimeFormatter sInstance = null;

  private String mSecondsAgoSuffix;
  private String mMinutesAgoSuffix;
  private String mHoursAgoSuffix;
  private String mDaysAgoSuffix;
  private String mMonthsAgoSuffix;
  private String mYearsAgoSuffix;
  private SimpleDateFormat mSimpleDateFormat;
  private DateFormat mDateFormat;

  private RemainsDateTimeFormatter(
      @NonNull String mSecondsAgoSuffix, @NonNull String mMinutesAgoSuffix,
      @NonNull String mHoursAgoSuffix, @NonNull String mDaysAgoSuffix,
      @NonNull String mMonthsAgoSuffix, @NonNull String mYearsAgoSuffix,
      String mDateFormat, @DateTimePatterns int datePattern, @DateTimePatterns int timePattern) {
    this.mSecondsAgoSuffix = mSecondsAgoSuffix;
    this.mMinutesAgoSuffix = mMinutesAgoSuffix;
    this.mHoursAgoSuffix = mHoursAgoSuffix;
    this.mDaysAgoSuffix = mDaysAgoSuffix;
    this.mMonthsAgoSuffix = mMonthsAgoSuffix;
    this.mYearsAgoSuffix = mYearsAgoSuffix;

    if (!TextUtils.isEmpty(mDateFormat)) {
      mSimpleDateFormat = new SimpleDateFormat(mDateFormat, Locale.getDefault());
    } else {
      this.mDateFormat = DateFormat
          .getDateTimeInstance(datePattern, timePattern, Locale.getDefault());
    }
  }

  private static String getString(Context context, @StringRes int stringResId) {
    return context.getString(stringResId);
  }

  public static void init(Context context,
      @StringRes int secondsAgoSuffix, @StringRes int minutesAgoSuffix,
      @StringRes int hoursAgoSuffix, @StringRes int daysAgoSuffix,
      @StringRes int monthsAgoSuffix, @StringRes int yearsAgoSuffix,
      @StringRes int dateFormat) {
    String secondsAgoSuffixString = getString(context, secondsAgoSuffix);
    String minutesAgoSuffixString = getString(context, minutesAgoSuffix);
    String hoursAgoSuffixString = getString(context, hoursAgoSuffix);
    String daysAgoSuffixString = getString(context, daysAgoSuffix);
    String monthsAgoSuffixString = getString(context, monthsAgoSuffix);
    String yearsAgoSuffixString = getString(context, yearsAgoSuffix);
    String dateFormatString = getString(context, dateFormat);

    init(secondsAgoSuffixString, minutesAgoSuffixString, hoursAgoSuffixString, daysAgoSuffixString,
        monthsAgoSuffixString, yearsAgoSuffixString, dateFormatString);
  }

  public static void init(Context context,
      @StringRes int secondsAgoSuffix, @StringRes int minutesAgoSuffix,
      @StringRes int hoursAgoSuffix, @StringRes int daysAgoSuffix,
      @StringRes int monthsAgoSuffix, @StringRes int yearsAgoSuffix,
      @DateTimePatterns int datePattern, @DateTimePatterns int timePattern) {
    String secondsAgoSuffixString = getString(context, secondsAgoSuffix);
    String minutesAgoSuffixString = getString(context, minutesAgoSuffix);
    String hoursAgoSuffixString = getString(context, hoursAgoSuffix);
    String daysAgoSuffixString = getString(context, daysAgoSuffix);
    String monthsAgoSuffixString = getString(context, monthsAgoSuffix);
    String yearsAgoSuffixString = getString(context, yearsAgoSuffix);

    init(secondsAgoSuffixString, minutesAgoSuffixString, hoursAgoSuffixString, daysAgoSuffixString,
        monthsAgoSuffixString, yearsAgoSuffixString, datePattern, timePattern);
  }

  public static void init(@NonNull String secondsAgoSuffix, @NonNull String minutesAgoSuffix,
      @NonNull String hoursAgoSuffix, @NonNull String daysAgoSuffix,
      @NonNull String monthsAgoSuffix, @NonNull String yearsAgoSuffix,
      @NonNull String dateFormat) {
    sInstance = new RemainsDateTimeFormatter(secondsAgoSuffix, minutesAgoSuffix, hoursAgoSuffix,
        daysAgoSuffix,
        monthsAgoSuffix,
        yearsAgoSuffix,
        dateFormat, DateTimePatterns.SHORT, DateTimePatterns.SHORT);
  }

  public static void init(@NonNull String secondsAgoSuffix, @NonNull String minutesAgoSuffix,
      @NonNull String hoursAgoSuffix, @NonNull String daysAgoSuffix,
      @NonNull String monthsAgoSuffix, @NonNull String yearsAgoSuffix,
      @DateTimePatterns int datePattern, @DateTimePatterns int timePattern) {
    sInstance = new RemainsDateTimeFormatter(secondsAgoSuffix, minutesAgoSuffix, hoursAgoSuffix,
        daysAgoSuffix,
        monthsAgoSuffix,
        yearsAgoSuffix,
        null, datePattern, timePattern);
  }

  public static RemainsDateTimeFormatter getInstance() {
    if (sInstance == null) {
      throw new IllegalStateException("Not initialized!");
    }

    return sInstance;
  }

  public String format(@NonNull Date targetDate) {
    Date currentDate = new Date();

    long diff = Math.abs(currentDate.getTime() - targetDate.getTime());
    long sec = diff / 1000;

    if (sec < 60) {
      return sec + mSecondsAgoSuffix;
    }

    long min = sec / 60;

    if (min < 60) {
      return min + mMinutesAgoSuffix;
    }

    long hour = min / 60;

    if (hour < 24) {
      return hour + mHoursAgoSuffix;
    }

    long day = hour / 24;

    if (day < 30) {
      return day + mDaysAgoSuffix;
    }

    long month = day / 30;

    if (month < 12) {
      return month + mMonthsAgoSuffix;
    }

    long year = month / 12;

    if (year == 1) {
      return 1 + mYearsAgoSuffix;
    } else if (mSimpleDateFormat != null) {
      return mSimpleDateFormat.format(targetDate);
    } else {
      return mDateFormat.format(targetDate);
    }
  }
}
