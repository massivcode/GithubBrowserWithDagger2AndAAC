package com.massivcode.githubbrowserwithdagger2andaac.utils.images;

import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;

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

  public static void clear(ImageView targetImageView) {
    GlideApp
        .with(targetImageView.getContext().getApplicationContext())
        .clear(targetImageView);
  }
}
