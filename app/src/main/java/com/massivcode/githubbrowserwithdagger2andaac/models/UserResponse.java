package com.massivcode.githubbrowserwithdagger2andaac.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class UserResponse extends BaseUserResponse {

  private String name;
  private String company;
  private String blog;
  private String location;
  private String email;
  private String bio;

  @SerializedName("public_repos")
  private int publicRepositoryCounts;

  @SerializedName("public_gists")
  private int publicGistCounts;

  private int followers;
  private int following;

  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("updated_at")
  private Date updatedAt;

  public String getName() {
    return name;
  }

  public String getCompany() {
    return company;
  }

  public String getBlog() {
    return blog;
  }

  public String getLocation() {
    return location;
  }

  public String getEmail() {
    return email;
  }

  public String getBio() {
    return bio;
  }

  public int getPublicRepositoryCounts() {
    return publicRepositoryCounts;
  }

  public int getPublicGistCounts() {
    return publicGistCounts;
  }

  public int getFollowers() {
    return followers;
  }

  public int getFollowing() {
    return following;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {
    return "UserResponse{" +
        "name='" + name + '\'' +
        ", company='" + company + '\'' +
        ", blog='" + blog + '\'' +
        ", location='" + location + '\'' +
        ", email='" + email + '\'' +
        ", bio='" + bio + '\'' +
        ", publicRepositoryCounts=" + publicRepositoryCounts +
        ", publicGistCounts=" + publicGistCounts +
        ", followers=" + followers +
        ", following=" + following +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
