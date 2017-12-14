package com.massivcode.githubbrowserwithdagger2andaac.services;

import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public interface RepoService {

  @GET("repos/{loginName}/{repositoryName}/contents/{path}")
  Call<List<RepositoryContentsResponse>> fetchContents(
      @Path("loginName") String loginName,
      @Path("repositoryName") String repositoryName,
      @Path("path") String path
  );



}
