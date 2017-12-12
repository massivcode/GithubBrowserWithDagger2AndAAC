package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class GistFile extends RealmObject {
  @PrimaryKey
  private String id;
  private String fileName;
  private String type;
  private String language;
  private String rawUrl;
  private long size;

  public GistFile() {
  }

  public GistFile(String gistId, String fileName, String type, String language, String rawUrl,
      long size) {
    this.id = gistId + "-" + fileName;
    this.fileName = fileName;
    this.type = type;
    this.language = language;
    this.rawUrl = rawUrl;
    this.size = size;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getRawUrl() {
    return rawUrl;
  }

  public void setRawUrl(String rawUrl) {
    this.rawUrl = rawUrl;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "GistFile{" +
        "id='" + id + '\'' +
        ", fileName='" + fileName + '\'' +
        ", type='" + type + '\'' +
        ", language='" + language + '\'' +
        ", rawUrl='" + rawUrl + '\'' +
        ", size=" + size +
        '}';
  }
}
