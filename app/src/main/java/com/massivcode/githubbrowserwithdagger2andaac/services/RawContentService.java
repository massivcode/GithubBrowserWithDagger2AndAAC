package com.massivcode.githubbrowserwithdagger2andaac.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public interface RawContentService {
  String SERVER_URL = "https://raw.githubusercontent.com/";

  @GET("{loginName}/{repositoryName}/master/{fileName}")
  Call<String> fetchContent(
      @Path("loginName") String loginName,
      @Path("repositoryName") String repositoryName,
      @Path("fileName") String fileName
  );
}
