package com.massivcode.githubbrowserwithdagger2andaac.utils.images;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by massivcode@gmail.com on 2017. 12. 13. 12:14
 */

public class ImageLoader {

  private ImageLoader() {

  }

  public static void loadImage(ImageView targetImageView, String url) {
    GlideApp
        .with(targetImageView.getContext().getApplicationContext())
        .load(url)
        .into(targetImageView);
  }

  public static void loadImageAsCircleTransform(ImageView targetImageView, String url) {
    GlideApp
        .with(targetImageView.getContext().getApplicationContext())
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(targetImageView);
  }

  public static void loadImageAsDrawable(Context context, String url, SimpleTarget<Drawable> simpleTarget) {
    GlideApp
        .with(context.getApplicationContext())
        .asDrawable()
        .load(url)
        .into(simpleTarget);
  }

  public static void clear(ImageView targetImageView) {
    GlideApp
        .with(targetImageView.getContext().getApplicationContext())
        .clear(targetImageView);
  }
}
