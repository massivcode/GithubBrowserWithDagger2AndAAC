package com.massivcode.githubbrowserwithdagger2andaac.models.local.daos;

import android.arch.lifecycle.LiveData;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.SearchSuggestion;
import com.massivcode.githubbrowserwithdagger2andaac.utils.realm.BaseDao;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class SearchSuggestionDao extends BaseDao<SearchSuggestion> {

  public SearchSuggestionDao(Realm mRealm) {
    super(mRealm);
  }

  public void add(String keyword) {
    SearchSuggestion oldSearchSuggestion = mRealm.where(SearchSuggestion.class).equalTo("keyword", keyword).findFirst();

    if (oldSearchSuggestion != null) {
      mRealm.beginTransaction();
      oldSearchSuggestion.setSearchedAt(new Date());
      mRealm.commitTransaction();
      return;
    }

    SearchSuggestion searchSuggestion = new SearchSuggestion(keyword, new Date());

    mRealm.beginTransaction();
    mRealm.copyToRealm(searchSuggestion);
    mRealm.commitTransaction();
  }

  public LiveData<RealmResults<SearchSuggestion>> findAllSearchSuggestion() {
    return asLiveData(
        mRealm.where(SearchSuggestion.class).sort("searchedAt", Sort.DESCENDING).findAll());
  }
}
