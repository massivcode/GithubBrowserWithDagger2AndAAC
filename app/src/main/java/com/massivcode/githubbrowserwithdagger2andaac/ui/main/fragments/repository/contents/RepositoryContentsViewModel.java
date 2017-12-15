package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.contents;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.services.RepoService;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.contents.RepositoryContentsFragment.ActivityInteractor;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.Realm;
import java.util.List;
import java.util.Stack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryContentsViewModel extends ViewModel {

  private Realm mRealm;
  private RepoService mRepoService;
  private MutableLiveData<Stack<String>> mPathLiveData;
  private MutableLiveData<Resource<Stack<List<RepositoryContentsResponse>>>> mContentsLiveData;

  public RepositoryContentsViewModel() {
    mRealm = Realm.getDefaultInstance();

    NetworkModule networkModule = NetworkModule.getInstance();
    mRepoService = networkModule.provideRepoService();

    mPathLiveData = new MutableLiveData<>();
    Stack<String> pathStack = new Stack<>();
    pathStack.push("/");
    mPathLiveData.setValue(pathStack);

    mContentsLiveData = new MutableLiveData<>();
    Stack<List<RepositoryContentsResponse>> dataStack = new Stack<>();
    mContentsLiveData.setValue(Resource.loading(dataStack));
  }

  public LiveData<Stack<String>> getPathLiveData() {
    return mPathLiveData;
  }

  public void onRepositoryPathClick(String clickedPathString) {
    Stack<String> pathStack = mPathLiveData.getValue();
    Stack<List<RepositoryContentsResponse>> dataStack = mContentsLiveData.getValue().data;

    int clickedPathIndex = pathStack.indexOf(clickedPathString);

    if (clickedPathIndex != -1) {
      int maxIndex = pathStack.size() - 1;
      int popCount = maxIndex - clickedPathIndex;

      for (int i = 0; i < popCount; i++) {
        pathStack.pop();
        dataStack.pop();
      }

      mPathLiveData.setValue(pathStack);
      mContentsLiveData.setValue(Resource.success(dataStack));
    }

  }

  public LiveData<Resource<Stack<List<RepositoryContentsResponse>>>> getContentsLiveData(
      String loginName, String repositoryName) {
    mContentsLiveData.setValue(Resource.loading(mContentsLiveData.getValue().data));

    mRepoService.fetchContents(loginName, repositoryName, getPathForExplore()).enqueue(
        new Callback<List<RepositoryContentsResponse>>() {
          @Override
          public void onResponse(Call<List<RepositoryContentsResponse>> call,
              Response<List<RepositoryContentsResponse>> response) {
            if (!response.isSuccessful()) {
              mContentsLiveData
                  .setValue(Resource.error(response.message(), mContentsLiveData.getValue().data));
              return;
            }

            Stack<List<RepositoryContentsResponse>> dataStack = mContentsLiveData.getValue().data;
            dataStack.push(response.body());
            mContentsLiveData.setValue(Resource.success(dataStack));
          }

          @Override
          public void onFailure(Call<List<RepositoryContentsResponse>> call, Throwable t) {
            mContentsLiveData
                .setValue(Resource.error(t.getMessage(), mContentsLiveData.getValue().data));
          }
        });

    return mContentsLiveData;
  }

  private String getPathForExplore() {
    Stack<String> pathStack = mPathLiveData.getValue();

    StringBuilder pathBuilder = new StringBuilder();

    for (int i = 0; i < pathStack.size(); i++) {
      String eachPath = pathStack.get(i);

      if (eachPath.equals("/")) {
        continue;
      }

      pathBuilder.append(eachPath);

      if (i != pathStack.size() - 1) {
        pathBuilder.append("/");
      }
    }

   return pathBuilder.toString();
  }

  public void onRepositoryResponseClick(String loginName, String repositoryName,
      RepositoryContentsResponse clickedFile, ActivityInteractor activityInteractor) {
    if (clickedFile.getType().equals("file")) {
      activityInteractor
          .onRepositoryContentsFileClick(loginName, repositoryName, clickedFile.getPath());
    } else {
      onRepositoryDirectoryClick(clickedFile.getName());
    }
  }

  private void onRepositoryDirectoryClick(String fileName) {
    DLogger.d(fileName);

    Stack<String> pathStack = mPathLiveData.getValue();

    if (pathStack.indexOf(fileName) == -1) {
      DLogger.d("new path -> add");
      pathStack.push(fileName);
      mPathLiveData.setValue(pathStack);
    }
  }

  public int getPathStackCount() {
    return mPathLiveData.getValue().size();
  }

  public void onBackPressed() {
    Stack<String> pathStack = mPathLiveData.getValue();
    onRepositoryPathClick(pathStack.get(pathStack.size() - 2));
  }

  @Override
  protected void onCleared() {
    mRealm.close();
    super.onCleared();
  }

}
