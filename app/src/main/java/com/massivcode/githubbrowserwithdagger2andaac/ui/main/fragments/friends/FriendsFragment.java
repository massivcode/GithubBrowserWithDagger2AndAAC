package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.friends;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.BaseUserResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class FriendsFragment extends BaseFragment {

  public interface ActivityInteractor {

    void onFriendItemClick(FriendsFragment fragment, String loginName);
  }

  @BindView(R.id.friendsRv)
  RecyclerView mFriendsRecyclerView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  protected boolean mIsFollowers;
  private FriendsAdapter mFriendsAdapter;
  private ActivityInteractor mActivityInteractor;

  public static FriendsFragment newInstance(String loginName, boolean isFollowers) {
    FriendsFragment fragment = new FriendsFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    args.putBoolean("isFollowers", isFollowers);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();

    if (args != null) {
      mLoginName = args.getString("loginName");
      mIsFollowers = args.getBoolean("isFollowers");
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivityInteractor = (ActivityInteractor) context;
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_friends;
  }

  @Override
  public void onViewBind() {
    mFriendsAdapter = new FriendsAdapter(mOnFriendsItemClick);
    mFriendsRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mFriendsRecyclerView.setAdapter(mFriendsAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    FriendsViewModel friendsViewModel = ViewModelProviders.of(this).get(FriendsViewModel.class);

    friendsViewModel.getFriendsLiveData(mLoginName, mIsFollowers).observe(this,
        new Observer<Resource<List<BaseUserResponse>>>() {
          @Override
          public void onChanged(@Nullable Resource<List<BaseUserResponse>> listResource) {
            if (listResource == null) {
              return;
            }

            if (listResource.status == Status.LOADING) {
              mProgressBar.setVisibility(View.VISIBLE);
            } else {
              mProgressBar.setVisibility(View.GONE);
            }

            mFriendsAdapter.onDataChanged(listResource.data);
          }
        });
  }

  private View.OnClickListener mOnFriendsItemClick = new OnClickListener() {
    @Override
    public void onClick(View view) {
      BaseUserResponse baseUser = (BaseUserResponse) view.getTag();

      if (mActivityInteractor != null) {
        mActivityInteractor.onFriendItemClick(FriendsFragment.this, baseUser.getLoginName());
      }
    }
  };
}
