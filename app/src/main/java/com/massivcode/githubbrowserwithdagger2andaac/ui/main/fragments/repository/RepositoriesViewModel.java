package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.RepositoryDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.SearchSuggestionDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.RepoRepository;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.UserRepository;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class RepositoriesViewModel extends ViewModel {

  private Realm mRealm;
  private UserRepository mUserRepository;
  private RepoRepository mRepoRepository;

  public RepositoriesViewModel() {
    mRealm = Realm.getDefaultInstance();
    NetworkModule networkModule = NetworkModule.getInstance();
    mUserRepository = new UserRepository(networkModule.provideNetworkUtil(),
        networkModule.provideUserService(), new SearchSuggestionDao(mRealm), new UserDao(mRealm));
    mRepoRepository = new RepoRepository(networkModule.provideUserService(), new UserDao(mRealm),
        new RepositoryDao(mRealm));
  }

  public LiveData<Resource<User>> getUserLiveData(String loginName) {
    return mUserRepository.getUser(loginName);
  }

  public LiveData<Resource<List<Repository>>> getRepositoryLiveData(User user) {
    return mRepoRepository.getRepositories(user);
  }

  @Override
  protected void onCleared() {
    mRealm.close();
    super.onCleared();
  }
}
