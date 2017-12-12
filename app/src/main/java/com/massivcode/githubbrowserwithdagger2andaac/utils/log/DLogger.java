package com.massivcode.githubbrowserwithdagger2andaac.utils.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class DLogger {
  private static String TAG = "DLogger";
  private static boolean sIsInitialized = false;
  private static boolean sIsDebugMode = false;

  public static void init(Context context) {
    init(context, TAG);
  }

  public static void init(Context context, String tag) {
    sIsDebugMode = isDebugMode(context);
    sIsInitialized = true;
    TAG = tag;
  }

  private static boolean isDebugMode(Context context) {
    boolean debuggable = false;

    PackageManager pm = context.getPackageManager();
    try {
      ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
      debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
    } catch (NameNotFoundException e) {
      /* debuggable variable will remain false */
    }

    return debuggable;
  }

  /**
   * Log Level Error
   **/
  public static final void e(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.e(TAG, buildLogMsg(message));
    }
  }

  /**
   * Log Level Warning
   **/
  public static final void w(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.w(TAG, buildLogMsg(message));
    }
  }

  /**
   * Log Level Information
   **/
  public static final void i(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.i(TAG, buildLogMsg(message));
    }
  }

  /**
   * Log Level Debug
   **/
  public static final void d(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.d(TAG, buildLogMsg(message));
    }
  }

  /**
   * Log Level Verbose
   **/
  public static final void v(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.v(TAG, buildLogMsg(message));
    }
  }

  public static final void wtf(String message) {
    if (!sIsInitialized) {
      throw new IllegalStateException("Not Initialized!");
    }

    if (sIsDebugMode) {
      Log.wtf(TAG, buildLogMsg(message));
    }
  }


  private static String buildLogMsg(String message) {
    StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

    return ste.getFileName().replace(".java", "") +
        "." +
        ste.getMethodName() +
        "() : " +
        message;

  }

}
