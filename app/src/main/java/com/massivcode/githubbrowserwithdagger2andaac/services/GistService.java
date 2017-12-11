package com.massivcode.githubbrowserwithdagger2andaac.services;

import com.massivcode.githubbrowserwithdagger2andaac.models.GistCommentResponse;
import com.massivcode.githubbrowserwithdagger2andaac.models.GistResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public interface GistService {
  @GET("gists/{gistId}")
  Call<GistResponse> fetchGist(@Path("gistId") String gistId);

  @GET("gists/{gistId}/comments")
  Call<List<GistCommentResponse>> fetchGistComments(@Path("gistId") String gistId);
}
