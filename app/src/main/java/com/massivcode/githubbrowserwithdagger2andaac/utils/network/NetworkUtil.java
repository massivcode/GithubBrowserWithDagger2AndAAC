package com.massivcode.githubbrowserwithdagger2andaac.utils.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class NetworkUtil {
  private Application mApplication;

  public NetworkUtil(Application mApplication) {
    this.mApplication = mApplication;
  }

  public boolean checkNetworkState() {
    ConnectivityManager manager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (manager == null) {
      return false;
    }

    NetworkInfo networkInfo = manager.getActiveNetworkInfo();

    if (networkInfo == null) {
      return false;
    }

    int networkType = networkInfo.getType();
    return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE;
  }

}
