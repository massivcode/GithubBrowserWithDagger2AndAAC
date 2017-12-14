package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.RepositoryDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryResponse;
import com.massivcode.githubbrowserwithdagger2andaac.services.UserService;
import io.realm.RealmList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class RepoRepository {

  private UserService mUserService;
  private UserDao mUserDao;
  private RepositoryDao mRepositoryDao;

  public RepoRepository(
      UserService mUserService,
      UserDao mUserDao,
      RepositoryDao mRepositoryDao) {
    this.mUserService = mUserService;
    this.mUserDao = mUserDao;
    this.mRepositoryDao = mRepositoryDao;
  }

  public LiveData<Resource<List<Repository>>> getRepositories(final User user) {
    return new NetworkBoundResource<List<Repository>>() {

      @Override
      protected LiveData<List<Repository>> loadFromDb() {
        return mRepositoryDao.findAllRepositories(user.getLoginName());
      }

      @Override
      protected boolean shouldFetch(LiveData<List<Repository>> dbSource) {
        return dbSource == null || dbSource.getValue() == null || dbSource.getValue().size() == 0;
      }

      @Override
      protected MutableLiveData<List<Repository>> fetchFromServer() {
        final MutableLiveData<List<Repository>> mutableLiveData = new MutableLiveData<>();

        new RepositoryFetchAsyncTask(mUserService, user.getLoginName(), mutableLiveData).execute();

        return mutableLiveData;
      }

      @Override
      protected void saveCallResult(List<Repository> data) {
        RealmList<Repository> realmList = new RealmList<>();
        realmList.addAll(data);
        mUserDao.addRepositories(user, realmList);
      }
    }.getAsLiveData();
  }

  private static class RepositoryFetchAsyncTask extends
      AsyncTask<Void, Void, List<RepositoryResponse>> {

    private int mPage = 1;
    private UserService mUserService;
    private String mLoginName;
    private MutableLiveData<List<Repository>> mLiveData;

    public RepositoryFetchAsyncTask(
        UserService mUserService, String mLoginName,
        MutableLiveData<List<Repository>> mLiveData) {
      this.mUserService = mUserService;
      this.mLoginName = mLoginName;
      this.mLiveData = mLiveData;
    }

    @Override
    protected List<RepositoryResponse> doInBackground(Void... voids) {
      List<RepositoryResponse> repositoryResponses = new ArrayList<>();

      while (true) {
        Response<List<RepositoryResponse>> response = fetchSync();

        if (response == null) {
          break;
        }

        if (!response.isSuccessful()) {
          break;
        }

        List<RepositoryResponse> eachResponse = response.body();

        if (eachResponse == null || eachResponse.isEmpty()) {
          break;
        }

        repositoryResponses.addAll(eachResponse);
        mPage += 1;
      }

      Collections.sort(repositoryResponses);
      return repositoryResponses;
    }

    private Response<List<RepositoryResponse>> fetchSync() {
      try {
        return mUserService.fetchRepositories(mLoginName, mPage).execute();
      } catch (IOException e) {
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<RepositoryResponse> repositoryResponses) {
      super.onPostExecute(repositoryResponses);

      List<Repository> results = new ArrayList<>();

      for (RepositoryResponse each : repositoryResponses) {
        results.add(each.toRepository());
      }

      mLiveData.setValue(results);
    }
  }
}
