package com.massivcode.githubbrowserwithdagger2andaac.repositories;

import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.ERROR;
import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.LOADING;
import static com.massivcode.githubbrowserwithdagger2andaac.repositories.Status.SUCCESS;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * https://developer.android.com/topic/libraries/architecture/guide.html#addendum
 */
public class Resource<T> {
  public final Status status;
  @Nullable public final T data;
  @Nullable public final String message;
  private Resource(Status status, @Nullable T data, @Nullable String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static <T> Resource<T> success(@NonNull T data) {
    return new Resource<>(SUCCESS, data, null);
  }

  public static <T> Resource<T> error(String msg, @Nullable T data) {
    return new Resource<>(ERROR, data, msg);
  }

  public static <T> Resource<T> loading(@Nullable T data) {
    return new Resource<>(LOADING, data, null);
  }

  @Override
  public String toString() {
    return "Resource{" +
        "status=" + status +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }
}
