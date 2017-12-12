package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class SearchSuggestion extends RealmObject {
  @PrimaryKey
  private String keyword;

  private Date searchedAt;

  public SearchSuggestion() {
  }

  public SearchSuggestion(String keyword, Date searchedAt) {
    this.keyword = keyword;
    this.searchedAt = searchedAt;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public Date getSearchedAt() {
    return searchedAt;
  }

  public void setSearchedAt(Date searchedAt) {
    this.searchedAt = searchedAt;
  }

  @Override
  public String toString() {
    return "SearchSuggestion{" +
        "keyword='" + keyword + '\'' +
        ", searchedAt=" + searchedAt +
        '}';
  }
}
