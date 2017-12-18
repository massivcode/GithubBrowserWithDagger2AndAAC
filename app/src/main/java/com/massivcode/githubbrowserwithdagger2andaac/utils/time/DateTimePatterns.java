package com.massivcode.githubbrowserwithdagger2andaac.utils.time;

import static com.massivcode.githubbrowserwithdagger2andaac.utils.time.DateTimePatterns.FULL;
import static com.massivcode.githubbrowserwithdagger2andaac.utils.time.DateTimePatterns.LONG;
import static com.massivcode.githubbrowserwithdagger2andaac.utils.time.DateTimePatterns.MEDIUM;
import static com.massivcode.githubbrowserwithdagger2andaac.utils.time.DateTimePatterns.SHORT;

import android.support.annotation.IntDef;
import java.text.DateFormat;

/**
 * Created by massivcode@gmail.com on 2017-12-18.
 */

@IntDef({SHORT, MEDIUM, LONG, FULL})
public @interface DateTimePatterns {

  int SHORT = DateFormat.SHORT;
  int MEDIUM = DateFormat.MEDIUM;
  int LONG = DateFormat.LONG;
  int FULL = DateFormat.FULL;
}
