package com.massivcode.githubbrowserwithdagger2andaac.services;

import com.massivcode.githubbrowserwithdagger2andaac.models.remote.BaseUserResponse;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.GistResponse;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryResponse;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.UserResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public interface UserService {

  @GET("users/{loginName}")
  Call<UserResponse> fetchUser(@Path("loginName") String userLoginName);

  @GET("users/{loginName}/followers")
  Call<List<BaseUserResponse>> fetchFollowers(
      @Path("loginName") String userLoginName,
      @Query("page") int page);

  @GET("users/{loginName}/repos")
  Call<List<RepositoryResponse>> fetchRepositories(
      @Path("loginName") String userLoginName,
      @Query("page") int page);

  @GET("users/{loginName}/following")
  Call<List<BaseUserResponse>> fetchFollowing(
      @Path("loginName") String userLoginName,
      @Query("page") int page);

  @GET("users/{loginName}/gists")
  Call<List<GistResponse>> fetchGists(@Path("loginName") String userLoginName);
}
