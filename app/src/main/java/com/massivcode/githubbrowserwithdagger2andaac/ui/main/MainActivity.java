package com.massivcode.githubbrowserwithdagger2andaac.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

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
        ImageLoader.loadImage(mProfileImageView, user.getAvatarUrl());
      }
    });
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
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
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_repository) {
      // Handle the camera action
    } else if (id == R.id.nav_gists) {

    } else if (id == R.id.nav_followers) {

    } else if (id == R.id.nav_following) {
    }

    mDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }
}
