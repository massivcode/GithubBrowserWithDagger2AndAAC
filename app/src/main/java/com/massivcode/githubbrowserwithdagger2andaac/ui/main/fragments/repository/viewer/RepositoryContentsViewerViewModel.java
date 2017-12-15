package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.viewer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.services.RawContentService;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryContentsViewerViewModel extends ViewModel {

  private RawContentService mRawContentsService;
  private MutableLiveData<Resource<String>> mContentsLiveData;

  public RepositoryContentsViewerViewModel() {
    NetworkModule networkModule = NetworkModule.getInstance();
    mRawContentsService = networkModule.provideRawContentService();
    mContentsLiveData = new MutableLiveData<>();
  }

  public LiveData<Resource<String>> getContentsLiveData(String loginName, String repositoryName,
      String path) {
    if (mContentsLiveData.getValue() == null) {
      mContentsLiveData.setValue(Resource.<String>loading(null));

      mRawContentsService.fetchContent(loginName, repositoryName, path).enqueue(
          new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              if (!response.isSuccessful()) {
                mContentsLiveData.setValue(Resource.<String>error(response.message(), null));
              } else {
                mContentsLiveData.setValue(Resource.success(response.body()));
              }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              mContentsLiveData.setValue(Resource.<String>error(t.getMessage(), null));
            }
          });
    }

    return mContentsLiveData;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
