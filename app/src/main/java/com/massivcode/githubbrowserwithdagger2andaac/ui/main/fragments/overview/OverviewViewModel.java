package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.SearchSuggestionDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.UserRepository;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;

/**
 * Created by massivcode@gmail.com on 2017. 12. 13. 13:41
 */

public class OverviewViewModel extends ViewModel {

  private Realm mRealm;
  private UserRepository mUserRepository;

  public OverviewViewModel() {
    mRealm = Realm.getDefaultInstance();

    NetworkModule networkModule = NetworkModule.getInstance();
    mUserRepository = new UserRepository(networkModule.provideNetworkUtil(),
        networkModule.provideUserService(), new SearchSuggestionDao(mRealm), new UserDao(mRealm));
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
