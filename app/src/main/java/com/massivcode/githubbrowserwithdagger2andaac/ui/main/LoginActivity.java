package com.massivcode.githubbrowserwithdagger2andaac.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseActivity;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.viewmodels.LoginViewModel;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;

public class LoginActivity extends BaseActivity {

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  @BindView(R.id.loginNameAcact)
  AppCompatAutoCompleteTextView mLoginNameAppCompatAutoCompleteTextView;

  private LoginViewModel mViewModel;

  @Override
  public int setLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  public void onViewBind() {
    mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

    mLoginNameAppCompatAutoCompleteTextView.setOnEditorActionListener(
        mOnLoginNameEditorActionListener);
  }

  private OnEditorActionListener mOnLoginNameEditorActionListener = new OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
      String loginName = mLoginNameAppCompatAutoCompleteTextView.getText().toString();

      if (TextUtils.isEmpty(loginName)) {
        return false;
      }

      if (i == EditorInfo.IME_ACTION_SEARCH) {
        mViewModel.getUserLiveData(loginName).observe(LoginActivity.this, mUserObserver);
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mLoginNameAppCompatAutoCompleteTextView.getWindowToken(), 0);
        return true;
      }
      return false;
    }
  };

  private Observer<Resource<User>> mUserObserver = new Observer<Resource<User>>() {
    @Override
    public void onChanged(@Nullable Resource<User> userResource) {
      if (userResource == null) {
        return;
      }

      setProgressBarVisibility(userResource.status);

      DLogger.d("results->\t" + userResource);
    }
  };

  private void setProgressBarVisibility(Status status) {
    switch (status) {
      case LOADING:
        mProgressBar.setVisibility(View.VISIBLE);
        break;
      case ERROR:
      case SUCCESS:
        mProgressBar.setVisibility(View.GONE);
        break;
    }
  }

}
