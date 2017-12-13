package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017. 12. 13. 13:15
 */

public class OverviewMenuItem {

  @DrawableRes
  private int iconId;
  @StringRes
  private int titleId;
  private int counts;

  public OverviewMenuItem(@DrawableRes int iconId, @StringRes int titleId, int counts) {
    this.iconId = iconId;
    this.titleId = titleId;
    this.counts = counts;
  }

  public int getIconId() {
    return iconId;
  }

  public int getTitleId() {
    return titleId;
  }

  public int getCounts() {
    return counts;
  }

  public static List<OverviewMenuItem> toOverViewMenuItems(User user) {
    List<OverviewMenuItem> overviewMenuItems = new ArrayList<>();

    int repositoryCounts = user.getPublicRepositoryCounts();
    int gistCounts = user.getPublicGistCounts();
    int followersCount = user.getFollowers();
    int followingsCount = user.getFollowing();

    overviewMenuItems.add(new OverviewMenuItem(R.drawable.ic_repository, R.string.nav_repositories,
        repositoryCounts));
    overviewMenuItems
        .add(new OverviewMenuItem(R.drawable.ic_gists, R.string.nav_gists, gistCounts));
    overviewMenuItems
        .add(new OverviewMenuItem(R.drawable.ic_followers, R.string.nav_followers, followersCount));
    overviewMenuItems.add(
        new OverviewMenuItem(R.drawable.ic_following, R.string.nav_followings, followingsCount));

    return overviewMenuItems;
  }
}
