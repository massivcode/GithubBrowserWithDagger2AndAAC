package com.massivcode.githubbrowserwithdagger2andaac.models.remote;

import com.google.gson.annotations.SerializedName;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.License;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class LicenseResponse {
  private String key;
  private String name;
  @SerializedName("spdx_id")
  private String spdxId;
  private String url;

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public String getSpdxId() {
    return spdxId;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public String toString() {
    return "LicenseResponse{" +
        "key='" + key + '\'' +
        ", name='" + name + '\'' +
        ", spdxId='" + spdxId + '\'' +
        ", url='" + url + '\'' +
        '}';
  }

  public License toLicense() {
    return new License(key, name, spdxId, url);
  }
}
