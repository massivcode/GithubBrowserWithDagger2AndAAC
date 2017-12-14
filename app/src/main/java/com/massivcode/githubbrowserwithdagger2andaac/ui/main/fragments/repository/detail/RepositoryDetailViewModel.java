package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.daos.RepositoryDao;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.services.RawContentService;
import com.massivcode.githubbrowserwithdagger2andaac.services.RepoService;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class RepositoryDetailViewModel extends ViewModel {

  private Realm mRealm;
  private RepoService mRepoService;
  private RawContentService mRawContentService;
  private RepositoryDao mRepositoryDao;

  private MutableLiveData<Repository> mRepositoryLiveData;
  private MutableLiveData<Resource<RepositoryContentsResponse>> mReadMeLiveData;
  private MutableLiveData<Resource<String>> mReadMeContentsLiveData;

  public RepositoryDetailViewModel() {
    mRealm = Realm.getDefaultInstance();

    NetworkModule networkModule = NetworkModule.getInstance();
    mRepoService = networkModule.provideRepoService();
    mRawContentService = networkModule.provideRawContentService();
    mRepositoryDao = new RepositoryDao(mRealm);

    mRepositoryLiveData = new MutableLiveData<>();
    mReadMeLiveData = new MutableLiveData<>();
    mReadMeLiveData.setValue(null);
    mReadMeContentsLiveData = new MutableLiveData<>();
    mReadMeContentsLiveData.setValue(null);
  }

  public LiveData<Repository> findRepository(String loginName, long repositoryId) {
    if (mRepositoryLiveData.getValue() == null) {
      mRepositoryLiveData.setValue(mRepositoryDao.findRepository(loginName, repositoryId));
    }

    return mRepositoryLiveData;
  }

  public LiveData<Resource<RepositoryContentsResponse>> getReadMeLiveData(Repository repository) {
    if (mReadMeLiveData.getValue() == null) {
      mReadMeLiveData.setValue(Resource.<RepositoryContentsResponse>loading(null));

      mRepoService.fetchContents(repository.getOwner().getLoginName(), repository.getName(), "")
          .enqueue(
              new Callback<List<RepositoryContentsResponse>>() {
                @Override
                public void onResponse(Call<List<RepositoryContentsResponse>> call,
                    Response<List<RepositoryContentsResponse>> response) {
                  if (!response.isSuccessful()) {
                    mReadMeLiveData.setValue(
                        Resource.<RepositoryContentsResponse>error(response.message(), null));
                    return;
                  }

                  List<RepositoryContentsResponse> body = response.body();

                  if (body == null || body.isEmpty()) {
                    mReadMeLiveData
                        .setValue(Resource.<RepositoryContentsResponse>error("empty", null));
                    return;
                  }

                  RepositoryContentsResponse readMe = null;

                  for (RepositoryContentsResponse each : body) {
                    String name = each.getName();

                    DLogger.d(name);

                    if (name.toUpperCase().contains("README")) {
                      readMe = each;
                      break;
                    }
                  }

                  if (readMe == null) {
                    mReadMeLiveData.setValue(
                        Resource.<RepositoryContentsResponse>error("not found", null));
                  } else {
                    mReadMeLiveData.setValue(Resource.success(readMe));
                  }
                }

                @Override
                public void onFailure(Call<List<RepositoryContentsResponse>> call, Throwable t) {
                  mReadMeLiveData.setValue(
                      Resource.<RepositoryContentsResponse>error(t.getMessage(), null));
                }
              });
    }

    return mReadMeLiveData;
  }

  public MutableLiveData<Resource<String>> getReadMeContentsLiveData(String loginName, String repositoryName, String fileName) {
    if (mReadMeContentsLiveData.getValue() == null) {
      mReadMeContentsLiveData.setValue(Resource.<String>loading(null));

      mRawContentService.fetchContent(loginName, repositoryName, fileName).enqueue(
          new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

              if (!response.isSuccessful()) {
                mReadMeContentsLiveData.setValue(Resource.<String>error(response.message(), null));
                return;
              }

              String readMeMarkdown = response.body();

              if (TextUtils.isEmpty(readMeMarkdown)) {
                mReadMeContentsLiveData.setValue(Resource.<String>error("not found", null));
                return;
              }

              mReadMeContentsLiveData.setValue(Resource.success(readMeMarkdown));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              mReadMeContentsLiveData.setValue(Resource.<String>error(t.getMessage(), null));
            }
          });
    }

    return mReadMeContentsLiveData;
  }

  @Override
  protected void onCleared() {
    mRealm.close();
    super.onCleared();
  }
}
