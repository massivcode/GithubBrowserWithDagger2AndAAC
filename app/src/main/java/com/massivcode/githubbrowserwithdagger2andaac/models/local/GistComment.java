package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class GistComment extends RealmObject {
  @PrimaryKey
  private long id;

  private Owner user;

  private String authorAssociation;

  private Date createdAt;

  private Date updatedAt;

  private String body;

  public GistComment() {
  }

  public GistComment(long id, Owner user, String authorAssociation, Date createdAt,
      Date updatedAt, String body) {
    this.id = id;
    this.user = user;
    this.authorAssociation = authorAssociation;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.body = body;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Owner getUser() {
    return user;
  }

  public void setUser(Owner user) {
    this.user = user;
  }

  public String getAuthorAssociation() {
    return authorAssociation;
  }

  public void setAuthorAssociation(String authorAssociation) {
    this.authorAssociation = authorAssociation;
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "GistComment{" +
        "id=" + id +
        ", user=" + user +
        ", authorAssociation='" + authorAssociation + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", body='" + body + '\'' +
        '}';
  }
}
