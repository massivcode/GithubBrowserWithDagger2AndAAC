package com.massivcode.githubbrowserwithdagger2andaac.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public abstract class BaseActivity extends AppCompatActivity {


  @LayoutRes
  public abstract int setLayoutId();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(setLayoutId());
    ButterKnife.bind(this);
    onViewBind();
  }

  public abstract void onViewBind();

  protected void addFragment(@IdRes int container, Fragment fragment) {
    String tag = fragment.getClass().getSimpleName();

    getSupportFragmentManager()
        .beginTransaction()
        .add(container, fragment, tag)
        .addToBackStack(tag)
        .commit();
  }

  protected Fragment getFragment(Class targetClass) {
    String tag = targetClass.getSimpleName();

    FragmentManager fragmentManager = getSupportFragmentManager();
    return fragmentManager.findFragmentByTag(tag);
  }

  protected Fragment getCurrentFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();

    int backStackEntryCount = fragmentManager.getBackStackEntryCount();

    if (backStackEntryCount == 0) {
      return null;
    }

    FragmentManager.BackStackEntry backStackEntry = fragmentManager
        .getBackStackEntryAt(backStackEntryCount - 1);

    if (backStackEntry == null) {
      return null;
    }

    return fragmentManager.findFragmentByTag(backStackEntry.getName());
  }

  protected void clearAllBackStack() {
    FragmentManager supportFragmentManager = getSupportFragmentManager();

    for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
      supportFragmentManager.popBackStackImmediate();
    }
  }

  protected void hideFragment(Fragment fragment) {
    if (fragment == null) {
      return;
    }

    getSupportFragmentManager()
        .beginTransaction()
        .hide(fragment)
        .commit();
  }

  protected void showFragment(Fragment fragment) {
    if (fragment == null) {
      return;
    }

    getSupportFragmentManager()
        .beginTransaction()
        .show(fragment)
        .commit();
  }

  protected void popBackStack(Fragment currentFragment) {
    FragmentManager supportFragmentManager = getSupportFragmentManager();

    supportFragmentManager.popBackStackImmediate();

    supportFragmentManager
        .beginTransaction()
        .show(currentFragment)
        .commit();
  }

  protected int getFragmentBackStack() {
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    return supportFragmentManager.getBackStackEntryCount();
  }

  protected boolean isBackStackEmpty() {
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    int fragmentBackStackCount = supportFragmentManager.getBackStackEntryCount();

    if (fragmentBackStackCount == 1) {
      return true;
    }

    supportFragmentManager.popBackStackImmediate();
    return false;
  }

  protected void replaceFragment(@IdRes int containerId, Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(containerId, fragment)
        .commit();
  }
}
