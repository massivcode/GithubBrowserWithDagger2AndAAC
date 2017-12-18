package com.massivcode.githubbrowserwithdagger2andaac.models.local.daos;

import android.arch.lifecycle.MutableLiveData;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.utils.realm.BaseDao;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-18.
 */

public class GistDao extends BaseDao<Gist> {

  public GistDao(Realm mRealm) {
    super(mRealm);
  }

  public MutableLiveData<List<Gist>> findAllGists(String loginName) {
    RealmResults<Gist> searchResults =
        mRealm
            .where(Gist.class)
            .equalTo("owner.loginName", loginName)
            .sort("updatedAt", Sort.DESCENDING)
            .findAll();

    List<Gist> gistList = new ArrayList<>(searchResults);
    MutableLiveData<List<Gist>> mutableLiveData = new MutableLiveData<>();
    mutableLiveData.setValue(gistList);

    return mutableLiveData;
  }

}
