package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.friends;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.BaseUserResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.services.UserService;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class FriendsViewModel extends ViewModel {

  private Realm mRealm;
  private UserService mUserService;

  public FriendsViewModel() {
    mRealm = Realm.getDefaultInstance();

    NetworkModule networkModule = NetworkModule.getInstance();
    mUserService = networkModule.provideUserService();
  }


  public LiveData<Resource<List<BaseUserResponse>>> getFriendsLiveData(String loginName, boolean isFollowers) {
    final MutableLiveData<Resource<List<BaseUserResponse>>> liveData = new MutableLiveData<>();
    liveData.setValue(Resource.<List<BaseUserResponse>>loading(null));

    new FriendsFetchAsyncTask(mUserService, loginName, liveData).execute(isFollowers);

    return liveData;
  }

  private static class FriendsFetchAsyncTask extends
      AsyncTask<Boolean, Void, List<BaseUserResponse>> {

    private int mPage = 1;
    private UserService mUserService;
    private String mLoginName;
    private MutableLiveData<Resource<List<BaseUserResponse>>> mLiveData;

    public FriendsFetchAsyncTask(
        UserService mUserService, String mLoginName,
        MutableLiveData<Resource<List<BaseUserResponse>>> mLiveData) {
      this.mUserService = mUserService;
      this.mLoginName = mLoginName;
      this.mLiveData = mLiveData;
    }

    @Override
    protected List<BaseUserResponse> doInBackground(Boolean... booleans) {
      boolean isFollowers = booleans[0];

      List<BaseUserResponse> friendsResponses = new ArrayList<>();

      while (true) {
        Response<List<BaseUserResponse>> response = fetchSync(isFollowers);

        if (response == null) {
          break;
        }

        if (!response.isSuccessful()) {
          break;
        }

        List<BaseUserResponse> eachResponse = response.body();

        if (eachResponse == null || eachResponse.isEmpty()) {
          break;
        }

        friendsResponses.addAll(eachResponse);
        mPage += 1;
      }

      return friendsResponses;
    }

    private Response<List<BaseUserResponse>> fetchSync(boolean isFollowers) {
      try {
        if (isFollowers) {
          return mUserService.fetchFollowers(mLoginName, mPage).execute();
        }

        return mUserService.fetchFollowing(mLoginName, mPage).execute();
      } catch (IOException e) {
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<BaseUserResponse> friendsResponses) {
      super.onPostExecute(friendsResponses);

      if (friendsResponses.isEmpty()) {
        mLiveData.setValue(Resource.error("empty", friendsResponses));
      } else {
        mLiveData.setValue(Resource.success(friendsResponses));
      }
    }
  }

  @Override
  protected void onCleared() {
    mRealm.close();
    super.onCleared();
  }
}
