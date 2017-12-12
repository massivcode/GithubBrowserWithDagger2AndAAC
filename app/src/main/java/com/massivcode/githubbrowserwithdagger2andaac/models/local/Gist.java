package com.massivcode.githubbrowserwithdagger2andaac.models.local;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class Gist extends RealmObject {
  @PrimaryKey
  private String id;

  private RealmList<GistFile> files;
  private RealmList<GistComment> gistComments;

  @SerializedName("public")
  private boolean isPublic;

  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("updated_at")
  private Date updatedAt;

  private String description;
  private int comments;
  private String user;
  private Owner owner;

  @SerializedName("truncated")
  private boolean isTruncated;

  public Gist() {
  }

  public Gist(String id, boolean isPublic, Date createdAt, Date updatedAt,
      String description, int comments, String user,
      Owner owner, boolean isTruncated) {
    this.id = id;
    this.isPublic = isPublic;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.description = description;
    this.comments = comments;
    this.user = user;
    this.owner = owner;
    this.isTruncated = isTruncated;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RealmList<GistFile> getFiles() {
    return files;
  }

  public void setFiles(
      RealmList<GistFile> files) {
    this.files = files;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public boolean isTruncated() {
    return isTruncated;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public RealmList<GistComment> getGistComments() {
    return gistComments;
  }

  public void setGistComments(
      RealmList<GistComment> gistComments) {
    this.gistComments = gistComments;
  }

  @Override
  public String toString() {
    return "Gist{" +
        "id='" + id + '\'' +
        ", files=" + files +
        ", gistComments=" + gistComments +
        ", isPublic=" + isPublic +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", description='" + description + '\'' +
        ", comments=" + comments +
        ", user='" + user + '\'' +
        ", owner=" + owner +
        ", isTruncated=" + isTruncated +
        '}';
  }
}
