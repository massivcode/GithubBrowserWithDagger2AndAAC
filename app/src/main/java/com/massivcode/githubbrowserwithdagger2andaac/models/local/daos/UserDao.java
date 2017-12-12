package com.massivcode.githubbrowserwithdagger2andaac.models.local.daos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.utils.realm.BaseDao;
import io.realm.Realm;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class UserDao extends BaseDao<User> {

  public UserDao(Realm mRealm) {
    super(mRealm);
  }

  public void add(User user) {
    mRealm.beginTransaction();
    mRealm.copyToRealm(user);
    mRealm.commitTransaction();
  }

  public LiveData<User> findUser(String loginName) {
    MutableLiveData<User> liveData = new MutableLiveData<>();
    liveData.setValue(mRealm.where(User.class).equalTo("loginName", loginName).findFirst());
    return liveData;
  }


}
