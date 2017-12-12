package com.massivcode.githubbrowserwithdagger2andaac.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
  }
}
