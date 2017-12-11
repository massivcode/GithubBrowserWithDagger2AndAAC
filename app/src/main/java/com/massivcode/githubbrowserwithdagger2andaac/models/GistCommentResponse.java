package com.massivcode.githubbrowserwithdagger2andaac.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class GistCommentResponse {

  private long id;

  private BaseUserResponse user;

  @SerializedName("author_association")
  private String authorAssociation;

  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("updated_at")
  private Date updatedAt;

  private String body;

  public long getId() {
    return id;
  }

  public BaseUserResponse getUser() {
    return user;
  }

  public String getAuthorAssociation() {
    return authorAssociation;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "GistCommentResponse{" +
        "id=" + id +
        ", user=" + user +
        ", authorAssociation='" + authorAssociation + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", body='" + body + '\'' +
        '}';
  }
}
