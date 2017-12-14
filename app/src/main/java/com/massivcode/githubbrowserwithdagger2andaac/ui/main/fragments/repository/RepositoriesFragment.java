package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTextChanged.Callback;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.User;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.RepositoryAdapter.FilterResultsCallback;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-12.
 */

public class RepositoriesFragment extends BaseFragment {

  @BindView(R.id.searchAcet)
  AppCompatEditText mSearchEditText;

  @BindView(R.id.filterContainer)
  LinearLayout mFilterContainer;

  @BindView(R.id.filterResultTv)
  TextView mFilterResultsTextView;

  @BindView(R.id.repositoriesRv)
  RecyclerView mRepositoriesRecyclerView;

  @BindView(R.id.noSearchResultsTv)
  TextView mEmptySearchResultsTv;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  @BindString(R.string.filter_results_msg_format)
  String mFilterResultsMessageFormat;

  @BindString(R.string.search_results_empty_msg_format)
  String mSearchResultsEmptyMessageFormat;


  private String mLoginName;
  private RepositoriesViewModel mViewModel;
  private RepositoryAdapter mRepositoryAdapter;

  public static RepositoriesFragment newInstance(String loginName) {
    RepositoriesFragment fragment = new RepositoriesFragment();

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
    return R.layout.fragment_repository;
  }

  @Override
  public void onViewBind() {
    mRepositoryAdapter = new RepositoryAdapter(mOnRepositoryItemClickListener);
    mRepositoriesRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mRepositoriesRecyclerView.setAdapter(mRepositoryAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(RepositoriesViewModel.class);

    mViewModel.getUserLiveData(mLoginName).observe(this, mUserObserver);
  }

  private Observer<Resource<User>> mUserObserver = new Observer<Resource<User>>() {
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

      mViewModel.getRepositoryLiveData(userResource.data)
          .observe(RepositoriesFragment.this, mRepositoryObserver);
    }
  };

  private Observer<Resource<List<Repository>>> mRepositoryObserver = new Observer<Resource<List<Repository>>>() {
    @Override
    public void onChanged(@Nullable Resource<List<Repository>> listResource) {
      if (listResource == null) {
        return;
      }

      if (listResource.status == Status.LOADING) {
        mProgressBar.setVisibility(View.VISIBLE);
      } else {
        mProgressBar.setVisibility(View.GONE);
      }

      mRepositoryAdapter.onDataChanged(listResource.data);
    }
  };

  private View.OnClickListener mOnRepositoryItemClickListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      Repository item = (Repository) view.getTag();
    }
  };

  @OnClick(R.id.clearFilterTv)
  protected void onClearFilterClick(View view) {
    mEmptySearchResultsTv.setVisibility(View.GONE);
    mFilterContainer.setVisibility(View.GONE);
    mSearchEditText.setText(null);
    mRepositoryAdapter.setFilterMode(false);
  }

  @OnTextChanged(value = R.id.searchAcet, callback = Callback.AFTER_TEXT_CHANGED)
  protected void onSearchKeywordChanged(Editable editable) {
    String inputString = editable.toString();

    if (TextUtils.isEmpty(inputString)) {
      mEmptySearchResultsTv.setVisibility(View.GONE);
      mFilterContainer.setVisibility(View.GONE);
      mRepositoryAdapter.setFilterMode(false);
      return;
    }

    mRepositoryAdapter.onFiltered(inputString, mFilterResultsCallback);
  }

  private RepositoryAdapter.FilterResultsCallback mFilterResultsCallback = new FilterResultsCallback() {
    @Override
    public void onFilterCompleted(List<Repository> filtered, String keyword) {
      int resultCounts = filtered.size();

      if (resultCounts == 0) {
        mEmptySearchResultsTv.setVisibility(View.VISIBLE);
        mEmptySearchResultsTv.setText(mSearchResultsEmptyMessageFormat.replace("{L}", mLoginName));
      } else {
        mEmptySearchResultsTv.setVisibility(View.GONE);
      }

      mFilterContainer.setVisibility(View.VISIBLE);
      mFilterResultsTextView.setText(generateFilterResultsString(resultCounts, keyword));

      mRepositoryAdapter.onDataChanged(filtered);
    }
  };

  private SpannableStringBuilder generateFilterResultsString(int resultCounts, String keyword) {
    String format = mFilterResultsMessageFormat.replace("{C}", resultCounts + "").replace("{K}", keyword);
    SpannableStringBuilder builder = new SpannableStringBuilder(format);
    builder.setSpan(new ForegroundColorSpan(Color.BLACK), 0, format.indexOf(" "), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    builder.setSpan(new ForegroundColorSpan(Color.BLACK), format.lastIndexOf(" "), format.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    return builder;
  }
}
