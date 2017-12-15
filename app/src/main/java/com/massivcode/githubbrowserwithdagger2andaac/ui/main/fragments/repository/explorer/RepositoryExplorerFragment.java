package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.explorer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import java.util.List;
import java.util.Stack;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryExplorerFragment extends BaseFragment {

  public interface ActivityInteractor {

    void onRepositoryContentsFileClick(String loginName, String repositoryName, String path);
  }

  @BindView(R.id.repositoryPathRv)
  RecyclerView mRepositoryPathRecyclerView;

  @BindView(R.id.previousPathContainer)
  LinearLayout mPreviousPathContainer;

  @BindView(R.id.currentFilesRv)
  RecyclerView mRepositoryContentsRecyclerView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private String mRepositoryName;
  private ActivityInteractor mActivityInteractor;

  private RepositoryPathAdapter mRepositoryPathAdapter;
  private RepositoryExplorerAdapter mRepositoryExplorerAdapter;
  private RepositoryExplorerViewModel mViewModel;
  private boolean mIsRepositoryPathClick = false;

  public static RepositoryExplorerFragment newInstance(String loginName, String repositoryName) {
    RepositoryExplorerFragment fragment = new RepositoryExplorerFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    args.putString("repositoryName", repositoryName);
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
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivityInteractor = (ActivityInteractor) context;
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_repository_explorer;
  }

  @Override
  public void onViewBind() {
    mRepositoryPathAdapter = new RepositoryPathAdapter(mOnRepositoryPathClickListener);
    mRepositoryPathRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    mRepositoryPathRecyclerView.setAdapter(mRepositoryPathAdapter);

    mRepositoryExplorerAdapter = new RepositoryExplorerAdapter(mOnRepositoryContentsClickListener);
    mRepositoryContentsRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mRepositoryContentsRecyclerView.setAdapter(mRepositoryExplorerAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(RepositoryExplorerViewModel.class);

    mViewModel.getPathLiveData().observe(this, new Observer<Stack<String>>() {
      @Override
      public void onChanged(@Nullable Stack<String> strings) {
        mRepositoryPathAdapter.onDataChanged(strings);

        if (!mIsRepositoryPathClick) {
          DLogger.i(strings.toString());
          mViewModel.getContentsLiveData(mLoginName, mRepositoryName);
        }
      }
    });

    mViewModel.getContentsLiveData(mLoginName, mRepositoryName).observe(this,
        new Observer<Resource<Stack<List<RepositoryContentsResponse>>>>() {
          @Override
          public void onChanged(
              @Nullable Resource<Stack<List<RepositoryContentsResponse>>> stackResource) {
            if (stackResource == null) {
              return;
            }

            if (stackResource.status == Status.LOADING) {
              mProgressBar.setVisibility(View.VISIBLE);
            } else {
              mProgressBar.setVisibility(View.GONE);
            }

            Stack<List<RepositoryContentsResponse>> data = stackResource.data;

            if (data == null || data.isEmpty()) {
              return;
            }

            mRepositoryExplorerAdapter.onDataChanged(data.peek());
          }
        });
  }

  private View.OnClickListener mOnRepositoryPathClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      mIsRepositoryPathClick = true;
      mViewModel.onRepositoryPathClick((String) view.getTag());
    }
  };

  private View.OnClickListener mOnRepositoryContentsClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      mIsRepositoryPathClick = false;
      mViewModel.onRepositoryResponseClick(mLoginName, mRepositoryName,
          (RepositoryContentsResponse) view.getTag(), mActivityInteractor);
    }
  };

  public void onBackPressed() {
    mIsRepositoryPathClick = true;
    mViewModel.onBackPressed();
  }

  public int getPathStackCount() {
    return mViewModel.getPathStackCount();
  }
}
