package com.massivcode.githubbrowserwithdagger2andaac.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseActivity;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.friends.FriendsFragment;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview.OverviewFragment;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview.OverviewMenuItem;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.RepositoriesFragment;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.contents.RepositoryContentsFragment;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.detail.RepositoryDetailFragment;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    OverviewFragment.ActivityInteractor, RepositoriesFragment.ActivityInteractor,
    FriendsFragment.ActivityInteractor, RepositoryDetailFragment.ActivityInteractor,
    RepositoryContentsFragment.ActivityInteractor {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;

  @BindView(R.id.nav_view)
  NavigationView mNavigationView;

  private ImageView mProfileImageView;

  private TextView mLoginNameTextView;

  private TextView mLinkTextView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private MainViewModel mViewModel;
  private String mClickedLoginName;

  @Override
  public int setLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  public void onViewBind() {
    mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    View navHeaderView = mNavigationView.getHeaderView(0);
    mProfileImageView = navHeaderView.findViewById(R.id.profileIv);
    mLoginNameTextView = navHeaderView.findViewById(R.id.loginNameTv);
    mLinkTextView = navHeaderView.findViewById(R.id.linkTv);

    Intent receivedIntent = getIntent();
    mLoginName = receivedIntent.getStringExtra("loginName");

    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle(mLoginName);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    mNavigationView.setNavigationItemSelectedListener(this);

    mViewModel.getUserLiveData(mLoginName).observe(this, new Observer<Resource<User>>() {
      @Override
      public void onChanged(@Nullable Resource<User> userResource) {
        if (userResource == null) {
          return;
        }

        if (userResource.status == Status.LOADING) {
          mProgressBar.setVisibility(View.VISIBLE);
        } else {
          mProgressBar.setVisibility(View.GONE);
        }

        if (userResource.data == null) {
          return;
        }

        User user = userResource.data;
        mLoginNameTextView.setText(user.getLoginName());
        mLinkTextView
            .setText(TextUtils.isEmpty(user.getEmail()) ? user.getBlog() : user.getEmail());
        ImageLoader.loadImageAsCircleTransform(mProfileImageView, user.getAvatarUrl());
      }
    });

    addFragment(R.id.fragmentContainer, OverviewFragment.newInstance(mLoginName));
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else if (isBackStackEmpty()) {
      DLogger.i("isBackStackEmpty-> finish");
      finish();
    } else {
      Fragment currentFragment = getCurrentFragment();

      if (currentFragment == null) {
        super.onBackPressed();
      } else if (currentFragment instanceof RepositoryContentsFragment) {
        RepositoryContentsFragment fragment = (RepositoryContentsFragment) currentFragment;

        if (fragment.getPathStackCount() != 1) {
          fragment.onBackPressed();
          return;
        }

        super.onBackPressed();
      } else {
        super.onBackPressed();
      }
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    String loginName = TextUtils.isEmpty(mClickedLoginName) ? mLoginName : mClickedLoginName;

    int id = item.getItemId();

    if (id == R.id.nav_repository) {
      addFragment(R.id.fragmentContainer, RepositoriesFragment.newInstance(loginName));
    } else if (id == R.id.nav_gists) {
      DLogger.d("gists");
    } else if (id == R.id.nav_followers) {
      addFragment(R.id.fragmentContainer, FriendsFragment.newInstance(loginName, true));
    } else if (id == R.id.nav_following) {
      addFragment(R.id.fragmentContainer, FriendsFragment.newInstance(loginName, false));
    }

    mDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onOverviewMenuItemClicked(OverviewFragment fragment, OverviewMenuItem item,
      String loginName) {
    mClickedLoginName = loginName;

    switch (item.getIconId()) {
      case R.drawable.ic_repository:
        onNavigationItemSelected(getMenuItem(0));
        break;
      case R.drawable.ic_gists:
        onNavigationItemSelected(getMenuItem(1));
        break;
      case R.drawable.ic_followers:
        onNavigationItemSelected(getMenuItem(2));
        break;
      case R.drawable.ic_following:
        onNavigationItemSelected(getMenuItem(3));
        break;
    }
  }

  private MenuItem getMenuItem(int position) {
    MenuItem menuItem = mNavigationView.getMenu().getItem(position);
    menuItem.setChecked(true);
    return menuItem;
  }

  @Override
  public void onRepositoryItemClick(RepositoriesFragment fragment, Repository item,
      String loginName) {
    addFragment(R.id.fragmentContainer, RepositoryDetailFragment.newInstance(loginName, item.getId()));
  }

  @Override
  public void onFriendItemClick(FriendsFragment fragment, String loginName) {
    startActivity(new Intent(MainActivity.this, MainActivity.class)
        .putExtra("loginName", loginName));
  }

  @Override
  public void onViewCodeClick(RepositoryDetailFragment fragment, String loginName,
      String repositoryName) {
    addFragment(R.id.fragmentContainer, RepositoryContentsFragment.newInstance(loginName, repositoryName));
  }

  @Override
  public void onRepositoryContentsFileClick(String loginName, String repositoryName, String path) {

  }
}
