package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class Repository extends RealmObject {
  @PrimaryKey
  private long id;

  private String name;

  private String fullName;

  private Owner owner;

  private boolean isPrivate;

  private String htmlUrl;

  private String description;

  private boolean isFork;

  private Date createdAt;

  private Date updatedAt;

  private Date pushedAt;

  private String homepage;

  private long size;

  private int starCounts;

  private int watcherCounts;

  private String language;

  private int folkCounts;

  private int openIssueCounts;

  private License license;

  public Repository() {
  }

  public Repository(long id, String name, String fullName,
      Owner owner, boolean isPrivate, String htmlUrl, String description, boolean isFork,
      Date createdAt, Date updatedAt, Date pushedAt, String homepage, long size, int starCounts,
      int watcherCounts, String language, int folkCounts, int openIssueCounts,
      License license) {
    this.id = id;
    this.name = name;
    this.fullName = fullName;
    this.owner = owner;
    this.isPrivate = isPrivate;
    this.htmlUrl = htmlUrl;
    this.description = description;
    this.isFork = isFork;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.pushedAt = pushedAt;
    this.homepage = homepage;
    this.size = size;
    this.starCounts = starCounts;
    this.watcherCounts = watcherCounts;
    this.language = language;
    this.folkCounts = folkCounts;
    this.openIssueCounts = openIssueCounts;
    this.license = license;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isFork() {
    return isFork;
  }

  public void setFork(boolean fork) {
    isFork = fork;
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

  public Date getPushedAt() {
    return pushedAt;
  }

  public void setPushedAt(Date pushedAt) {
    this.pushedAt = pushedAt;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public int getStarCounts() {
    return starCounts;
  }

  public void setStarCounts(int starCounts) {
    this.starCounts = starCounts;
  }

  public int getWatcherCounts() {
    return watcherCounts;
  }

  public void setWatcherCounts(int watcherCounts) {
    this.watcherCounts = watcherCounts;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public int getFolkCounts() {
    return folkCounts;
  }

  public void setFolkCounts(int folkCounts) {
    this.folkCounts = folkCounts;
  }

  public int getOpenIssueCounts() {
    return openIssueCounts;
  }

  public void setOpenIssueCounts(int openIssueCounts) {
    this.openIssueCounts = openIssueCounts;
  }

  public License getLicense() {
    return license;
  }

  public void setLicense(License license) {
    this.license = license;
  }

  @Override
  public String toString() {
    return "Repository{" +
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
