package com.massivcode.githubbrowserwithdagger2andaac.models.local.daos;

import android.arch.lifecycle.MutableLiveData;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.utils.realm.BaseDao;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class RepositoryDao extends BaseDao<Repository> {

  public RepositoryDao(Realm mRealm) {
    super(mRealm);
  }

  public MutableLiveData<List<Repository>> findAllRepositories(String loginName) {
    RealmResults<Repository> searchResults =
        mRealm
        .where(Repository.class)
        .equalTo("owner.loginName", loginName)
        .sort("updatedAt", Sort.DESCENDING)
        .findAll();

    List<Repository> repositoryList = new ArrayList<>(searchResults);
    MutableLiveData<List<Repository>> mutableLiveData = new MutableLiveData<>();
    mutableLiveData.setValue(repositoryList);

    return mutableLiveData;
  }


}
