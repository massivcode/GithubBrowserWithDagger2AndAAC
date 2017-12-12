package com.massivcode.githubbrowserwithdagger2andaac.ui.main.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.SearchSuggestion;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.SearchSuggestionDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.UserRepository;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class LoginViewModel extends ViewModel {

  private Realm mRealm;
  private UserRepository mUserRepository;

  public LoginViewModel() {
    mRealm = Realm.getDefaultInstance();

    NetworkModule networkModule = NetworkModule.getInstance();
    mUserRepository = new UserRepository(networkModule.provideNetworkUtil(),
        networkModule.provideUserService(), new SearchSuggestionDao(mRealm), new UserDao(mRealm));
  }


  public LiveData<RealmResults<SearchSuggestion>> getSearchSuggestionLiveData() {
    return mUserRepository.getSearchSuggestionLiveData();
  }

  public void addSearchSuggestion(String keyword) {
    mUserRepository.addSearchSuggestion(keyword);
  }

  public LiveData<Resource<User>> getUserLiveData(String loginName) {
    return mUserRepository.getUser(loginName);
  }

  @Override
  protected void onCleared() {
    mRealm.close();
    super.onCleared();
  }
}
