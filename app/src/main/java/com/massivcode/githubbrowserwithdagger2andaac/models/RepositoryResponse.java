package com.massivcode.githubbrowserwithdagger2andaac.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class RepositoryResponse {
  private long id;

  private String name;

  @SerializedName("full_name")
  private String fullName;

  private BaseUserResponse owner;

  @SerializedName("private")
  private boolean isPrivate;

  @SerializedName("html_url")
  private String htmlUrl;

  private String description;

  @SerializedName("fork")
  private boolean isFork;

  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("updated_at")
  private Date updatedAt;

  @SerializedName("pushed_at")
  private Date pushedAt;

  private String homepage;

  private long size;

  @SerializedName("stargazers_count")
  private int starCounts;

  @SerializedName("watchers_count")
  private int watcherCounts;

  private String language;

  @SerializedName("forks_count")
  private int folkCounts;

  @SerializedName("open_issues_count")
  private int openIssueCounts;

  private String license;

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getFullName() {
    return fullName;
  }

  public BaseUserResponse getOwner() {
    return owner;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public String getDescription() {
    return description;
  }

  public boolean isFork() {
    return isFork;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getPushedAt() {
    return pushedAt;
  }

  public String getHomepage() {
    return homepage;
  }

  public long getSize() {
    return size;
  }

  public int getStarCounts() {
    return starCounts;
  }

  public int getWatcherCounts() {
    return watcherCounts;
  }

  public String getLanguage() {
    return language;
  }

  public int getFolkCounts() {
    return folkCounts;
  }

  public int getOpenIssueCounts() {
    return openIssueCounts;
  }

  public String getLicense() {
    return license;
  }

  @Override
  public String toString() {
    return "RepositoryResponse{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", fullName='" + fullName + '\'' +
        ", owner=" + owner +
        ", isPrivate=" + isPrivate +
        ", htmlUrl='" + htmlUrl + '\'' +
        ", description='" + description + '\'' +
        ", isFork=" + isFork +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", pushedAt=" + pushedAt +
        ", homepage='" + homepage + '\'' +
        ", size=" + size +
        ", starCounts=" + starCounts +
        ", watcherCounts=" + watcherCounts +
        ", language='" + language + '\'' +
        ", folkCounts=" + folkCounts +
        ", openIssueCounts=" + openIssueCounts +
        ", license='" + license + '\'' +
        '}';
  }
}
