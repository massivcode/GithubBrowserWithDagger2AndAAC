package com.massivcode.githubbrowserwithdagger2andaac.utils.colors;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class GithubLanguageColorMapper {

  private static GithubLanguageColorMapper sInstance = null;
  private LinkedHashMap<String, GithubLanguage> mGithubLanguageMap;

  private GithubLanguageColorMapper(LinkedHashMap<String, GithubLanguage> githubLanguageMap) {
    this.mGithubLanguageMap = githubLanguageMap;
    DLogger.d(githubLanguageMap.toString());
  }

  public static boolean init(Context context) {
    if (sInstance != null) {
      return false;
    }

    InputStream inputStream = null;

    try {
      inputStream = context.getAssets().open("github_colors.json");
      byte[] data = new byte[inputStream.available()];
      inputStream.read(data);

      String jsonString = new String(data, "UTF-8");
      JSONObject jsonObject = new JSONObject(jsonString);
      LinkedHashMap<String, GithubLanguage> githubLanguageMap = new LinkedHashMap<>();

      for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
        String key = it.next();
        JSONObject eachGithubLanguage = jsonObject.getJSONObject(key);
        String colorString = eachGithubLanguage.getString("color");
        String url = eachGithubLanguage.getString("url");
        githubLanguageMap.put(key, new GithubLanguage(colorString, url));
      }

      sInstance = new GithubLanguageColorMapper(githubLanguageMap);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (JSONException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return true;
  }

  public static GithubLanguageColorMapper getInstance() {
    if (sInstance == null) {
      throw new IllegalStateException("Not initialized!");
    }

    return sInstance;
  }

  public @ColorInt
  int findLanguageColorByName(String languageName) {
    GithubLanguage githubLanguage = mGithubLanguageMap.get(languageName);

    if (githubLanguage == null) {
      return Color.parseColor("#000000");
    }

    return githubLanguage.getColorInt();
  }

}
