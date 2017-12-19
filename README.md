# 깃허브 브라우저 샘플 앱

간단한 기능을 수행할 수 있는 깃허브 브라우저 앱

## 1. 개발환경

* IDE : Android Studio 3.0.1
* Language : Java

### 사용 라이브러리

* [Retrofit2](https://github.com/square/retrofit) : 통신 관련 라이브러리.
* [ButterKnife](https://github.com/JakeWharton/butterknife) : View DI 라이브러리.
* [Dagger2](https://github.com/google/dagger) : DI 라이브러리.
* [Realm Java](https://github.com/realm/realm-java) : 모바일 DB 라이브러리.
* [Glide](https://github.com/bumptech/glide) : 비동기 이미지 로더.
* [Android Architecture Components (Lifecycle Extensions)]()
* [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) : 통신 디버그 로거
* [github-colors](https://github.com/ozh/github-colors) : Github 에서 사용되는 언어색상 에셋
* [MarkdownView-Android](https://github.com/mukeshsolanki/MarkdownView-Android) : 마크다운 뷰

## 2. 지원기능

- [X] 사용자 프로필 조회
- [X] 사용자 gist 조회
- [X] 사용자 gist 내용 출력
- [X] 사용자 gist 댓글 출력
- [X] 사용자 팔로워, 팔로잉 조회
- [x] 저장소 조회
- [X] 저장소 파일 탐색 / 파일 내용 출력
- [ ] Dagger2 적용
- [ ] 네트워크 에러 핸들링

## 3. 데이터 캐싱 관련 메모

현재 데이터 캐싱 관련 로직은 다음과 같다.

1. 로컬 소스로부터 데이터 로드
2. 로컬 데이터가 null 또는 비어있을 경우 네트워크 소스로부터 데이터 fetching
3. 네트워크 소스로부터 데이터 fetching 완료시 로컬 소스에 데이터 삽입

대략 위와 같은 구조로 진행되고 있는데, 위의 로직을 수행하는 코드는 다음과 같은 구조다.

```java
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

```

구글이 제공하는 코드에서는 Room Persistence Library 를 사용하고 있어서 로컬 소스 관련된 작업을 워커 스레드에서 진행하나,
이 프로젝트는 Realm 을 사용하고 있어서 메인 스레드에서 진행한다.

물론 Realm 에서도 비동기 트랜잭션으로 데이터를 삽입할 수 있으나 샘플 앱이라서 해당 코드는 적용하지 않았다.

또한 구글측 샘플코드에서는 LiveData 를 이용하여 네트워크 통신 작업을 수행할 때 Retrofit 에 LiveDataAdapter 를
적용하여 프로세스가 진행되었으나, 여기서는 그러한 내용을 적용하지 않았다.

네트워크 통신 작업의 경우 당연히 비동기적으로 수행해야하고, 따라서 진행 상태와 에러를 유저에게 알려줘야 한다.

관련 작업 Mapping 클래스는 구글이 제공한 아래의 클래스를 이용했다.

```java
package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.ERROR;
import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.LOADING;
import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.SUCCESS;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * https://developer.android.com/topic/libraries/architecture/guide.html#addendum
 */
public class Resource<T> {
  public final Status status;
  @Nullable public final T data;
  @Nullable public final String message;
  private Resource(Status status, @Nullable T data, @Nullable String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static <T> Resource<T> success(@NonNull T data) {
    return new Resource<>(SUCCESS, data, null);
  }

  public static <T> Resource<T> error(String msg, @Nullable T data) {
    return new Resource<>(ERROR, data, msg);
  }

  public static <T> Resource<T> loading(@Nullable T data) {
    return new Resource<>(LOADING, data, null);
  }

  @Override
  public String toString() {
    return "Resource{" +
        "status=" + status +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }
}
```

작업 상태는 3가지로 나뉘어져 있는데 각각 로딩, 성공, 실패이며 해당 enum 클래스의 내용은 다음과 같다.

```java
package com.massivcode.githubbrowserwithdagger2andaac.repositories;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public enum Status {
  SUCCESS,
  ERROR,
  LOADING
}

```

이 프로젝트에서는 데이터를 로컬에 캐싱하여 이후 해당 데이터 요청시에는 로컬로부터 데이터를 로드하여 재사용하는
식으로 진행하고 있는데, fetch 여부 체크 로직이 로컬 데이터의 null 또는 empty 라서 실제 깃허브에서 데이터가
변경되었다고 fetch 할 일은 없다.

여기서 문제점이 하나 발견되었고, 이걸 해결하기 위해선 User 모델에 데이터 삽입 일시를 넣어 매번 fetch 한 사용자
데이터의 업데이트 날짜와의 비교를 통하여 데이터를 갱신해야 할 것 같다.

하지만 limit 이 시간당 60건이라 굳이 그렇게까지 해야하나 싶기도하다.
추가적으로 특정 View 라던가, pull to refresh 를 통하여 데이터를 갱신해도 좋을 것 같다.

두번째 문제로 페이지네이션인데, 저장소나 gist 를 fetch 할 때 현재 코드에서는 마지막 페이지에 도달할 때까지 (404 뜰 때 까지)
요청하여 일괄적으로 로컬에 저장하고 있다.

이 부분은 API 상에서 최대 페이지를 제공하고 있지 않아서 이렇게 작성했는데, Repository 패턴을 적용하고 있어서
딱히 해결방안이 떠오르지 않았다.