package com.massivcode.githubbrowserwithdagger2andaac.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class BaseUserResponse {
  private long id;
  @SerializedName("login")
  private String loginName;

  @SerializedName("avatar_url")
  private String avatarUrl;

  @SerializedName("gravatar_id")
  private String gravatarId;

  @SerializedName("html_url")
  private String htmlUrl;

  private String type;

  public long getId() {
    return id;
  }

  public String getLoginName() {
    return loginName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getGravatarId() {
    return gravatarId;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "BaseUserResponse{" +
        "id=" + id +
        ", loginName='" + loginName + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", gravatarId='" + gravatarId + '\'' +
        ", htmlUrl='" + htmlUrl + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
