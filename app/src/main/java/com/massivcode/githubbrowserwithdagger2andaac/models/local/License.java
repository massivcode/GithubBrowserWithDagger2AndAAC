package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class License extends RealmObject {
  @PrimaryKey
  private String key;
  private String name;
  private String spdxId;
  private String url;

  public License() {
  }

  public License(String key, String name, String spdxId, String url) {
    this.key = key;
    this.name = name;
    this.spdxId = spdxId;
    this.url = url;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpdxId() {
    return spdxId;
  }

  public void setSpdxId(String spdxId) {
    this.spdxId = spdxId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "License{" +
        "key='" + key + '\'' +
        ", name='" + name + '\'' +
        ", spdxId='" + spdxId + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
