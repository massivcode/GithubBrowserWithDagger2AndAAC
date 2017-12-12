package com.massivcode.githubbrowserwithdagger2andaac.utils.network;

import android.app.Application;
import com.massivcode.githubbrowserwithdagger2andaac.BuildConfig;
import com.massivcode.githubbrowserwithdagger2andaac.services.GistService;
import com.massivcode.githubbrowserwithdagger2andaac.services.RepoService;
import com.massivcode.githubbrowserwithdagger2andaac.services.UserService;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */
public class NetworkModule {

  private static NetworkModule sInstance = null;
  private OkHttpClient mOkHttpClient;
  private Retrofit mRetrofit;
  private UserService mUserService;
  private RepoService mRepoService;
  private GistService mGistService;
  private NetworkUtil mNetworkUtil;

  private NetworkModule(Application application) {
    mOkHttpClient = createOkHttpClient();
    mRetrofit = createRetrofit(mOkHttpClient);
    mUserService = createUserService();
    mRepoService = createRepoService();
    mGistService= createGistService();
    mNetworkUtil = createNetworkUtil(application);
  }

  public static void init(Application application) {
    if (sInstance == null) {
      sInstance = new NetworkModule(application);
    }
  }

  public static NetworkModule getInstance() {
    if (sInstance == null) {
      throw new IllegalStateException("not initialized!");
    }

    return sInstance;
  }

  private OkHttpClient createOkHttpClient() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();

    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(logging);
    }

    builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
        .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

    return builder.build();
  }

  private Retrofit createRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .build();
  }


  private UserService createUserService() {
    return mRetrofit.create(UserService.class);
  }

  private RepoService createRepoService() {
    return mRetrofit.create(RepoService.class);
  }

  private GistService createGistService() {
    return mRetrofit.create(GistService.class);
  }

  private NetworkUtil createNetworkUtil(Application application) {
    return new NetworkUtil(application);
  }

  public UserService provideUserService() {
    return mUserService;
  }

  public RepoService provideRepoService() {
    return mRepoService;
  }

  public GistService provideGistService() {
    return mGistService;
  }


  public OkHttpClient provideOkHttpClient() {
    return mOkHttpClient;
  }

  public Retrofit provideRetrofit() {
    return mRetrofit;
  }

  public NetworkUtil provideNetworkUtil() {
    return mNetworkUtil;
  }

}
