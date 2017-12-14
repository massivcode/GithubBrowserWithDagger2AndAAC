package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 *
 * https://developer.android.com/topic/libraries/architecture/guide.html#addendum
 */
public abstract class NetworkBoundResource<LocalResultType> {

  private final MediatorLiveData<Resource<LocalResultType>> mResult = new MediatorLiveData<>();

  public NetworkBoundResource() {
    mResult.setValue(Resource.<LocalResultType>loading(null));
    DLogger.d("loading start");

    final LiveData<LocalResultType> dbSource = loadFromDb();

    mResult.addSource(dbSource, new Observer<LocalResultType>() {
      @Override
      public void onChanged(@Nullable LocalResultType localResultType) {
        mResult.setValue(Resource.loading(localResultType));
      }
    });

    final boolean needFetch = shouldFetch(dbSource);

    if (needFetch) {
      DLogger.d("needFetch.");
      final LiveData<LocalResultType> serverSource = fetchFromServer();
      mResult.removeSource(dbSource);
      mResult.addSource(serverSource, new Observer<LocalResultType>() {
        @Override
        public void onChanged(@Nullable LocalResultType localResultType) {
          if (localResultType == null) {

            if (needFetch) {
              DLogger.d("fetchFailed 1. use dbSource. loading end.");
              mResult.setValue(Resource.error("err", dbSource.getValue()));
            } else {
              DLogger.d("fetchFailed 2. use dbSource. loading end.");
              mResult.setValue(Resource.success(dbSource.getValue()));
            }
          } else {
            DLogger.d("fetchSuccess. loading end.");
            mResult.removeSource(serverSource);
            mResult.setValue(Resource.success(localResultType));
            saveCallResult(localResultType);
          }
        }
      });
    } else {
      mResult.removeSource(dbSource);
      DLogger.d("not need Fetch. setValue from dbSource");
      mResult.setValue(Resource.success(dbSource.getValue()));
    }
  }

  protected abstract LiveData<LocalResultType> loadFromDb();

  protected abstract boolean shouldFetch(LiveData<LocalResultType> dbSource);

  protected abstract MutableLiveData<LocalResultType> fetchFromServer();

  protected abstract void saveCallResult(LocalResultType data);

  public LiveData<Resource<LocalResultType>> getAsLiveData() {
    return mResult;
  }
}
