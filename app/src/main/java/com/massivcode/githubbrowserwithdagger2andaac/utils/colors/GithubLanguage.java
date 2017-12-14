package com.massivcode.githubbrowserwithdagger2andaac.utils.colors;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class GithubLanguage {
  @SerializedName("color")
  private String colorString;

  private String url;

  public GithubLanguage(String colorString, String url) {
    this.colorString = colorString;
    this.url = url;
  }

  public String getColorString() {
    return colorString;
  }

  @ColorInt
  public int getColorInt() {
    return Color.parseColor(TextUtils.isEmpty(colorString) ? "#000000" : colorString);
  }

  public void setColorString(String colorString) {
    this.colorString = colorString;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "GithubLanguage{" +
        "colorString='" + colorString + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
