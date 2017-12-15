package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.viewer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.mukesh.MarkdownView;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryContentsViewerFragment extends BaseFragment {

  @BindView(R.id.filePathTv)
  TextView mFilePathTextView;

  @BindView(R.id.markdownView)
  MarkdownView mMarkdownView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private String mRepositoryName;
  private String mFilePath;

  public static RepositoryContentsViewerFragment newInstance(String loginName,
      String repositoryName, String path) {
    RepositoryContentsViewerFragment fragment = new RepositoryContentsViewerFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    args.putString("repositoryName", repositoryName);
    args.putString("path", path);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();

    if (args != null) {
      mLoginName = args.getString("loginName");
      mRepositoryName = args.getString("repositoryName");
      mFilePath = args.getString("path");
    }
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_repository_contents_viewer;
  }

  @Override
  public void onViewBind() {
    mFilePathTextView.setText(mFilePath);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    RepositoryContentsViewerViewModel viewModel = ViewModelProviders.of(this)
        .get(RepositoryContentsViewerViewModel.class);

    viewModel.getContentsLiveData(mLoginName, mRepositoryName, mFilePath).observe(this,
        new Observer<Resource<String>>() {
          @Override
          public void onChanged(@Nullable Resource<String> stringResource) {
            if (stringResource == null) {
              return;
            }

            if (stringResource.status == Status.LOADING) {
              mProgressBar.setVisibility(View.VISIBLE);
            } else {
              mProgressBar.setVisibility(View.GONE);
            }

            String data = stringResource.data;

            if (TextUtils.isEmpty(data)) {
              return;
            }

            mMarkdownView.setMarkDownText(data);
          }
        });
  }
}
