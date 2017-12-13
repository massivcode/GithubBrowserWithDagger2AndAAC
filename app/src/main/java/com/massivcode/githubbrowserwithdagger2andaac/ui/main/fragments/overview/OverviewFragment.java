package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class OverviewFragment extends BaseFragment {

  public interface ActivityInteractor {

    void onOverviewMenuItemClicked(OverviewFragment fragment, OverviewMenuItem item);
  }

  @BindView(R.id.profileIv)
  ImageView mProfileImageView;

  @BindView(R.id.userNameTv)
  TextView mUserNameTextView;

  @BindView(R.id.loginNameTv)
  TextView mLoginNameTextView;

  @BindView(R.id.locationTv)
  TextView mLocationTextView;

  @BindView(R.id.emailTv)
  TextView mEmailTextView;

  @BindView(R.id.homepageTv)
  TextView mHomepageTextView;

  @BindView(R.id.menuRv)
  RecyclerView mMenuRecyclerView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private OverviewViewModel mViewModel;
  private OverviewMenuAdapter mMenuAdapter;
  private ActivityInteractor mInteractor;
  private String mLoginName;

  public static OverviewFragment newInstance(String loginName) {
    OverviewFragment fragment = new OverviewFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();

    if (args != null) {
      mLoginName = args.getString("loginName");
    }
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_overview;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mInteractor = (ActivityInteractor) context;
  }

  @Override
  public void onViewBind() {
    DLogger.d("");
    mMenuAdapter = new OverviewMenuAdapter(mOnMenuItemClickListener);
    mMenuRecyclerView.setAdapter(mMenuAdapter);
    mMenuRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    DLogger.d("");
    mViewModel = ViewModelProviders.of(this).get(OverviewViewModel.class);

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

        ImageLoader.loadImageAsCircleTransform(mProfileImageView, user.getAvatarUrl());

        setTextViewState(mLoginNameTextView, user.getLoginName());
        setTextViewState(mUserNameTextView, user.getName());
        setTextViewState(mLocationTextView, user.getLocation());
        setTextViewState(mEmailTextView, user.getEmail());
        setTextViewState(mHomepageTextView, user.getBlog());

        mMenuAdapter.onDataChanged(OverviewMenuItem.toOverViewMenuItems(user));
      }
    });
  }

  private void setTextViewState(TextView targetTextView, String value) {
    if (TextUtils.isEmpty(value)) {
      targetTextView.setVisibility(View.GONE);
    } else {
      targetTextView.setVisibility(View.VISIBLE);
      targetTextView.setText(value);
    }
  }

  private View.OnClickListener mOnMenuItemClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      OverviewMenuItem item = (OverviewMenuItem) view.getTag();

      if (mInteractor != null) {
        mInteractor.onOverviewMenuItemClicked(OverviewFragment.this, item);
      }
    }
  };
}
