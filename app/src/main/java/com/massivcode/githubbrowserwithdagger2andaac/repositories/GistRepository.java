package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.GistDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.GistCommentResponse;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.GistResponse;
import com.massivcode.githubbrowserwithdagger2andaac.services.GistService;
import com.massivcode.githubbrowserwithdagger2andaac.services.UserService;
import io.realm.RealmList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-18.
 */

public class GistRepository {

  private GistDao mGistDao;
  private UserDao mUserDao;
  private GistService mGistService;
  private UserService mUserService;

  public GistRepository(
      GistDao mGistDao,
      UserDao mUserDao,
      GistService mGistService,
      UserService mUserService) {
    this.mGistDao = mGistDao;
    this.mUserDao = mUserDao;
    this.mGistService = mGistService;
    this.mUserService = mUserService;
  }

  public LiveData<Resource<List<Gist>>> getGists(final User user) {
    return new NetworkBoundResource<List<Gist>>() {

      @Override
      protected LiveData<List<Gist>> loadFromDb() {
        return mGistDao.findAllGists(user.getLoginName());
      }

      @Override
      protected boolean shouldFetch(LiveData<List<Gist>> dbSource) {
        return dbSource == null || dbSource.getValue() == null || dbSource.getValue().isEmpty();
      }

      @Override
      protected MutableLiveData<List<Gist>> fetchFromServer() {
        final MutableLiveData<List<Gist>> mutableLiveData = new MutableLiveData<>();

        new GistsFetchAsyncTask(mUserService, mGistService, user.getLoginName(), mutableLiveData).execute();

        return mutableLiveData;
      }

      @Override
      protected void saveCallResult(List<Gist> data) {
        RealmList<Gist> gistRealmList = new RealmList<>();
        gistRealmList.addAll(data);

        mUserDao.addGists(user, gistRealmList);
      }
    }.getAsLiveData();
  }


  private static class GistsFetchAsyncTask extends
      AsyncTask<Void, Void, List<GistResponse>> {

    private int mPage = 1;
    private UserService mUserService;
    private GistService mGistService;
    private String mLoginName;
    private MutableLiveData<List<Gist>> mLiveData;

    public GistsFetchAsyncTask(
        UserService mUserService, GistService mGistService, String mLoginName,
        MutableLiveData<List<Gist>> mLiveData) {
      this.mUserService = mUserService;
      this.mGistService = mGistService;
      this.mLoginName = mLoginName;
      this.mLiveData = mLiveData;
    }

    @Override
    protected List<GistResponse> doInBackground(Void... voids) {
      List<GistResponse> repositoryResponses = new ArrayList<>();

      while (true) {
        Response<List<GistResponse>> response = fetchSync();

        if (response == null) {
          break;
        }

        if (!response.isSuccessful()) {
          break;
        }

        List<GistResponse> eachResponse = response.body();

        if (eachResponse == null || eachResponse.isEmpty()) {
          break;
        }

        for (int i = 0; i < eachResponse.size(); i++) {
          GistResponse eachGistResponse = eachResponse.get(i);
          int eachCommentCounts = eachGistResponse.getComments();

          if (eachCommentCounts != 0) {
            String eachGistId = eachGistResponse.getId();
            Response<List<GistCommentResponse>> gistCommentResponse = fetchComments(eachGistId);

            if (gistCommentResponse != null) {
              eachGistResponse.setCommentsResponse(gistCommentResponse.body());
            }
          }

        }

        repositoryResponses.addAll(eachResponse);
        mPage += 1;
      }

      Collections.sort(repositoryResponses);
      return repositoryResponses;
    }

    private Response<List<GistResponse>> fetchSync() {
      try {
        return mUserService.fetchGists(mLoginName, mPage).execute();
      } catch (IOException e) {
        return null;
      }
    }

    private Response<List<GistCommentResponse>> fetchComments(String gistId) {
      try {
        return mGistService.fetchGistComments(gistId).execute();
      } catch (IOException e) {
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<GistResponse> repositoryResponses) {
      super.onPostExecute(repositoryResponses);

      List<Gist> results = new ArrayList<>();

      for (GistResponse each : repositoryResponses) {
        results.add(each.toGist());
      }

      mLiveData.setValue(results);
    }
  }

  public LiveData<Resource<Gist>> getGist(final User user, final String gistId) {
    return new NetworkBoundResource<Gist>() {

      @Override
      protected LiveData<Gist> loadFromDb() {
        return mGistDao.findGist(user.getLoginName(), gistId);
      }

      @Override
      protected boolean shouldFetch(LiveData<Gist> dbSource) {
        return dbSource == null || dbSource.getValue() == null;
      }

      @Override
      protected MutableLiveData<Gist> fetchFromServer() {
        final MutableLiveData<Gist> gistMutableLiveData = new MutableLiveData<>();

        mGistService.fetchGist(gistId).enqueue(new Callback<GistResponse>() {
          @Override
          public void onResponse(Call<GistResponse> call, Response<GistResponse> response) {
            if (response.isSuccessful()) {
              GistResponse gistResponse = response.body();

              if (gistResponse != null) {
                gistMutableLiveData.setValue(gistResponse.toGist());
              }
            }
          }

          @Override
          public void onFailure(Call<GistResponse> call, Throwable t) {

          }
        });

        return gistMutableLiveData;
      }

      @Override
      protected void saveCallResult(Gist data) {
      }
    }.getAsLiveData();
  }
}
