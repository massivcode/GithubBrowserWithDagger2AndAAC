package com.massivcode.githubbrowserwithdagger2andaac.utils.realm;

import android.arch.lifecycle.LiveData;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 *
 * https://github.com/ericmaxwell2003/android-persistence/blob/master/app/src/main/java/com/example/android/persistence/codelab/realmdb/utils/LiveRealmData.java
 */

public class RealmLiveData <T extends RealmModel> extends LiveData<RealmResults<T>> {
  private RealmResults<T> results;

  private final RealmChangeListener<RealmResults<T>> listener = new RealmChangeListener<RealmResults<T>>() {
    @Override
    public void onChange(RealmResults<T> results) {
      setValue(results);
    }
  };

  public RealmLiveData(RealmResults<T> realmResults) {
    results = realmResults;
    setValue(realmResults);
  }

  @Override
  protected void onActive() {
    DLogger.d("");
    results.addChangeListener(listener);
  }

  @Override
  protected void onInactive() {
    DLogger.d("");
    results.removeChangeListener(listener);
  }
}
