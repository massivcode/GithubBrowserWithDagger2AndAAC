package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class User extends RealmObject {
  @PrimaryKey
  private long id;

  private String loginName;

  private String avatarUrl;

  private String gravatarId;

  private String htmlUrl;

  private String type;

  private String name;
  private String company;
  private String blog;
  private String location;
  private String email;
  private String bio;

  private int publicRepositoryCounts;

  private int publicGistCounts;

  private int followers;
  private int following;

  private Date createdAt;

  private Date updatedAt;

  private RealmList<Repository> repositories;
  private RealmList<Gist> gists;

  public User() {
  }

  public User(long id, String loginName, String avatarUrl, String gravatarId,
      String htmlUrl, String type, String name, String company, String blog,
      String location, String email, String bio, int publicRepositoryCounts, int publicGistCounts,
      int followers, int following, Date createdAt, Date updatedAt) {
    this.id = id;
    this.loginName = loginName;
    this.avatarUrl = avatarUrl;
    this.gravatarId = gravatarId;
    this.htmlUrl = htmlUrl;
    this.type = type;
    this.name = name;
    this.company = company;
    this.blog = blog;
    this.location = location;
    this.email = email;
    this.bio = bio;
    this.publicRepositoryCounts = publicRepositoryCounts;
    this.publicGistCounts = publicGistCounts;
    this.followers = followers;
    this.following = following;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getGravatarId() {
    return gravatarId;
  }

  public void setGravatarId(String gravatarId) {
    this.gravatarId = gravatarId;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getBlog() {
    return blog;
  }

  public void setBlog(String blog) {
    this.blog = blog;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public int getPublicRepositoryCounts() {
    return publicRepositoryCounts;
  }

  public void setPublicRepositoryCounts(int publicRepositoryCounts) {
    this.publicRepositoryCounts = publicRepositoryCounts;
  }

  public int getPublicGistCounts() {
    return publicGistCounts;
  }

  public void setPublicGistCounts(int publicGistCounts) {
    this.publicGistCounts = publicGistCounts;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public RealmList<Repository> getRepositories() {
    return repositories;
  }

  public void setRepositories(
      RealmList<Repository> repositories) {
    this.repositories = repositories;
  }

  public RealmList<Gist> getGists() {
    return gists;
  }

  public void setGists(
      RealmList<Gist> gists) {
    this.gists = gists;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", loginName='" + loginName + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", gravatarId='" + gravatarId + '\'' +
        ", htmlUrl='" + htmlUrl + '\'' +
        ", type='" + type + '\'' +
        ", name='" + name + '\'' +
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
        ", repositories=" + repositories +
        ", gists=" + gists +
        '}';
  }
}
