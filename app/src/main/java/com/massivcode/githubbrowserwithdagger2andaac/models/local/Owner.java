package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class Owner extends RealmObject {
  @PrimaryKey
  private long id;

  private String loginName;

  private String avatarUrl;

  private String gravatarId;

  private String htmlUrl;

  private String type;

  public Owner() {
  }

  public Owner(long id, String loginName, String avatarUrl, String gravatarId,
      String htmlUrl, String type) {
    this.id = id;
    this.loginName = loginName;
    this.avatarUrl = avatarUrl;
    this.gravatarId = gravatarId;
    this.htmlUrl = htmlUrl;
    this.type = type;
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

  @Override
  public String toString() {
    return "Owner{" +
        "id=" + id +
        ", loginName='" + loginName + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", gravatarId='" + gravatarId + '\'' +
        ", htmlUrl='" + htmlUrl + '\'' +
        ", type='" + type + '\'' +
        '}';
  }

}
