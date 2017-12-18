package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class GistsFragment extends BaseFragment {

  @BindView(R.id.gistsRv)
  RecyclerView mGistsRecyclerView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private GistsAdapter mGistsAdapter;

  public static GistsFragment newInstance(String loginName) {
    GistsFragment fragment = new GistsFragment();

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
    return R.layout.fragment_gists;
  }

  @Override
  public void onViewBind() {
    mGistsAdapter = new GistsAdapter(mOnGistClickListener);
    mGistsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mGistsRecyclerView.setAdapter(mGistsAdapter);
  }

  private View.OnClickListener mOnGistClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      Gist clickedGist = (Gist) view.getTag();
    }
  };

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final GistsViewModel viewModel = ViewModelProviders.of(this).get(GistsViewModel.class);

    viewModel.getUserLiveData(mLoginName).observe(this, new Observer<Resource<User>>() {
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

        User user = userResource.data;

        if (user == null) {
          return;
        }

        viewModel.getGistLiveData(user).observe(GistsFragment.this, new Observer<Resource<List<Gist>>>() {
          @Override
          public void onChanged(@Nullable Resource<List<Gist>> listResource) {
            if (listResource == null) {
              return;
            }

            if (listResource.status == Status.LOADING) {
              mProgressBar.setVisibility(View.VISIBLE);
            } else {
              mProgressBar.setVisibility(View.GONE);
            }

            mGistsAdapter.onDataChanged(listResource.data);
          }
        });

      }
    });

  }


}
