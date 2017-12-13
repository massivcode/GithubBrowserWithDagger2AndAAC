package com.massivcode.githubbrowserwithdagger2andaac.utils.images;

import android.widget.ImageView;

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
}
