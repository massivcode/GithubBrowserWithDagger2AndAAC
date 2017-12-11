package com.massivcode.githubbrowserwithdagger2andaac.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class GistFileResponse {
  @SerializedName("filename")
  private String fileName;
  private String type;
  private String language;
  @SerializedName("raw_url")
  private String rawUrl;
  private long size;

  public String getFileName() {
    return fileName;
  }

  public String getType() {
    return type;
  }

  public String getLanguage() {
    return language;
  }

  public String getRawUrl() {
    return rawUrl;
  }

  public long getSize() {
    return size;
  }

  @Override
  public String toString() {
    return "GistFileResponse{" +
        "fileName='" + fileName + '\'' +
        ", type='" + type + '\'' +
        ", language='" + language + '\'' +
        ", rawUrl='" + rawUrl + '\'' +
        ", size=" + size +
        '}';
  }
}
