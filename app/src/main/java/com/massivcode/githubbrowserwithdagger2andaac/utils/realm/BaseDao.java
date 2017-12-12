package com.massivcode.githubbrowserwithdagger2andaac.utils.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class BaseDao<T extends RealmObject> {
  protected Realm mRealm;

  public BaseDao(Realm mRealm) {
    this.mRealm = mRealm;
  }

  protected long maxId(Class clazz, String idColumnName) {
    Number maxId = mRealm.where(clazz).max(idColumnName);

    if (maxId == null) {
      return 1;
    } else {
      return maxId.longValue() + 1;
    }
  }

  //  https://github.com/ericmaxwell2003/android-persistence/blob/master/app/src/main/java/com/example/android/persistence/codelab/realmdb/utils/Realm%2BDao.kt
  protected RealmLiveData<T> asLiveData(RealmResults<T> data) {
    return new RealmLiveData<>(data);
  }
}
