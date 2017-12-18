package com.massivcode.githubbrowserwithdagger2andaac.models.remote;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.GistComment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.GistFile;
import io.realm.RealmList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-11.
 */

public class GistResponse implements Comparable<GistResponse> {

  private String id;
  private LinkedHashMap<String, GistFileResponse> files;

  @SerializedName("public")
  private boolean isPublic;

  @SerializedName("created_at")
  private Date createdAt;

  @SerializedName("updated_at")
  private Date updatedAt;

  private String description;
  private int comments;
  private String user;
  private BaseUserResponse owner;

  @SerializedName("truncated")
  private boolean isTruncated;

  private List<GistCommentResponse> commentsResponse;

  public String getId() {
    return id;
  }

  public LinkedHashMap<String, GistFileResponse> getFiles() {
    return files;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public String getDescription() {
    return description;
  }

  public int getComments() {
    return comments;
  }

  public String getUser() {
    return user;
  }

  public BaseUserResponse getOwner() {
    return owner;
  }

  public boolean isTruncated() {
    return isTruncated;
  }

  public List<GistCommentResponse> getCommentsResponse() {
    return commentsResponse;
  }

  public void setCommentsResponse(
      List<GistCommentResponse> commentsResponse) {
    this.commentsResponse = commentsResponse;
  }

  @Override
  public String toString() {
    return "GistResponse{" +
        "id='" + id + '\'' +
        ", files=" + files +
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

  public Gist toGist() {
    RealmList<GistFile> gistFiles = new RealmList<>();

    if (files != null && !files.isEmpty()) {
      Collection<GistFileResponse> gistFileResponses = files.values();

      for (GistFileResponse each : gistFileResponses) {
        GistFile gistFile = each.toGistFile(getId());
        gistFiles.add(gistFile);
      }
    }

    RealmList<GistComment> gistComments = new RealmList<>();

    if (commentsResponse != null && !commentsResponse.isEmpty()) {
      for (GistCommentResponse each : commentsResponse) {
        gistComments.add(each.toGistComment());
      }
    }

    Gist gist = new Gist(id, isPublic, createdAt, updatedAt, description, comments, user, owner.toOwner(), isTruncated);
    gist.setFiles(gistFiles);
    gist.setGistComments(gistComments);
    return gist;
  }

  @Override
  public int compareTo(@NonNull GistResponse gistResponse) {
    Date targetUpdatedAt = gistResponse.updatedAt;

    if (updatedAt.equals(targetUpdatedAt)) {
      return 0;
    }

    if (updatedAt.after(targetUpdatedAt)) {
      return -1;
    } else {
      return 1;
    }
  }
}
