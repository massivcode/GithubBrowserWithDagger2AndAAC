package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.viewer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;

/**
 * Created by massivcode@gmail.com on 2017-12-19.
 */

public class GistViewerFragment extends BaseFragment {

  @BindView(R.id.gistFilesRv)
  RecyclerView mGistFilesRecyclerView;

  @BindView(R.id.gistCommentsRv)
  RecyclerView mGistCommentsRecyclerView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private String mGistId;
  private GistFilesAdapter mGistFilesAdapter;
  private GistCommentsAdapter mGistCommentsAdapter;

  public static GistViewerFragment newInstance(String loginName, String gistId) {
    GistViewerFragment fragment = new GistViewerFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    args.putString("gistId", gistId);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();

    if (args != null) {
      mLoginName = args.getString("loginName");
      mGistId = args.getString("gistId");
    }
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_gist_viewer;
  }

  @Override
  public void onViewBind() {
    mGistFilesAdapter = new GistFilesAdapter();
    mGistFilesRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mGistFilesRecyclerView.setAdapter(mGistFilesAdapter);

    mGistCommentsAdapter = new GistCommentsAdapter();
    mGistCommentsRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mGistCommentsRecyclerView.setAdapter(mGistCommentsAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final GistViewerViewModel viewModel = ViewModelProviders.of(this)
        .get(GistViewerViewModel.class);

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

        viewModel.getGistLiveData(user, mGistId).observe(GistViewerFragment.this,
            new Observer<Resource<Gist>>() {
              @Override
              public void onChanged(@Nullable Resource<Gist> gistResource) {
                if (gistResource == null) {
                  return;
                }

                if (gistResource.status == Status.LOADING) {
                  mProgressBar.setVisibility(View.VISIBLE);
                } else {
                  mProgressBar.setVisibility(View.GONE);
                }

                Gist gist = gistResource.data;

                if (gist == null) {
                  return;
                }

                mGistFilesAdapter.onDataChanged(gist.getFiles());
                mGistCommentsAdapter.onDataChanged(gist.getGistComments());
              }
            });
      }
    });
  }
}
