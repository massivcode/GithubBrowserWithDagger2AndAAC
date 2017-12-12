package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.SearchSuggestion;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.SearchSuggestionDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.UserDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.UserResponse;
import com.massivcode.githubbrowserwithdagger2andaac.services.UserService;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkUtil;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class UserRepository {

  private NetworkUtil mNetworkUtil;
  private UserService mUserService;
  private SearchSuggestionDao mSearchSuggestionDao;
  private UserDao mUserDao;

  public UserRepository(NetworkUtil networkUtil, UserService userService,
      SearchSuggestionDao searchSuggestionDao, UserDao userDao) {
    mNetworkUtil = networkUtil;
    mUserService = userService;
    mSearchSuggestionDao = searchSuggestionDao;
    mUserDao = userDao;
  }

  public LiveData<RealmResults<SearchSuggestion>> getSearchSuggestionLiveData() {
    return mSearchSuggestionDao.findAllSearchSuggestion();
  }

  public void addSearchSuggestion(String keyword) {
    mSearchSuggestionDao.add(keyword);
  }

  public LiveData<Resource<User>> getUser(final String loginName) {
    return new NetworkBoundResource<User>() {

      @Override
      protected LiveData<User> loadFromDb() {
        return mUserDao.findUser(loginName);
      }

      @Override
      protected boolean shouldFetch(LiveData<User> dbSource) {
        return dbSource == null || dbSource.getValue() == null;
      }

      @Override
      protected MutableLiveData<User> fetchFromServer() {
        final MutableLiveData<User> liveData = new MutableLiveData<>();

        mUserService.fetchUser(loginName).enqueue(new Callback<UserResponse>() {
          @Override
          public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
            if (response.isSuccessful()) {
              UserResponse userResponse = response.body();

              if (userResponse == null) {
                liveData.setValue(null);
              } else {
                liveData.setValue(userResponse.toUser());
              }
            } else {
              liveData.setValue(null);
            }
          }

          @Override
          public void onFailure(Call<UserResponse> call, Throwable t) {
            liveData.setValue(null);
          }
        });

        return liveData;
      }

      @Override
      protected void saveCallResult(User data) {
        mUserDao.add(data);
      }
    }.getAsLiveData();
  }

}
