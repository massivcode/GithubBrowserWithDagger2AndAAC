package com.massivcode.githubbrowserwithdagger2andaac;

import android.app.Application;
import com.massivcode.githubbrowserwithdagger2andaac.utils.colors.GithubLanguageColorMapper;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import com.massivcode.githubbrowserwithdagger2andaac.utils.time.DateTimePatterns;
import com.massivcode.githubbrowserwithdagger2andaac.utils.time.RemainsDateTimeFormatter;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class GithubBrowserApp extends Application {


  @Override
  public void onCreate() {
    super.onCreate();

    initRealm();
    initUtils();
  }


  private void initRealm() {
    Realm.init(this);

    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
        .schemaVersion(1)
        .build();

    Realm.setDefaultConfiguration(realmConfiguration);

  }

  private void initUtils() {
    DLogger.init(getApplicationContext());
    NetworkModule.init(this);

    boolean initSuccessful = GithubLanguageColorMapper.init(this);
    RemainsDateTimeFormatter
        .init(" seconds ago", " minutes ago",
            " hours ago", " days ago",
            " months ago", " years ago",
            DateTimePatterns.SHORT, DateTimePatterns.SHORT);
  }


}
