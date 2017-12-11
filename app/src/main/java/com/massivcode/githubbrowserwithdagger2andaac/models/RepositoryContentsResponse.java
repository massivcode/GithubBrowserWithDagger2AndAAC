package com.massivcode.githubbrowserwithdagger2andaac.models;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class RepositoryContentsResponse {
  private String name;
  private String path;
  private String sha;
  private long size;
  private String type;

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getSha() {
    return sha;
  }

  public long getSize() {
    return size;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "RepositoryContentsResponse{" +
        "name='" + name + '\'' +
        ", path='" + path + '\'' +
        ", sha='" + sha + '\'' +
        ", size=" + size +
        ", type='" + type + '\'' +
        '}';
  }
}
