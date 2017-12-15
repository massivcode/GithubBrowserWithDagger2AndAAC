package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.base.BaseFragment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.License;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Resource;
import com.massivcode.githubbrowserwithdagger2andaac.repositories.Status;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.MainActivity;
import com.massivcode.githubbrowserwithdagger2andaac.utils.colors.GithubLanguageColorMapper;
import com.massivcode.githubbrowserwithdagger2andaac.utils.log.DLogger;
import com.mukesh.MarkdownView;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class RepositoryDetailFragment extends BaseFragment {

  public interface ActivityInteractor {

    void onViewCodeClick(RepositoryDetailFragment fragment, String loginName,
        String repositoryName);
  }

  @BindView(R.id.loginNameTv)
  TextView mLoginNameTextView;

  @BindView(R.id.repositoryNameTv)
  TextView mRepositoryNameTextView;

  @BindView(R.id.repositoryDescriptionTv)
  TextView mRepositoryDescriptionTextView;

  @BindView(R.id.languageDivider)
  View mLanguageDivider;

  @BindView(R.id.starCountsTv)
  TextView mStarCountsTextView;

  @BindView(R.id.watcherCountsTv)
  TextView mWatcherCountsTextView;

  @BindView(R.id.forkCountsTv)
  TextView mForkCountsTextView;

  @BindView(R.id.licenseTv)
  TextView mLicenseTextView;

  @BindView(R.id.createdAtTv)
  TextView mCreatedAtTextView;

  @BindView(R.id.updatedAtTv)
  TextView mUpdatedAtTextView;

  @BindView(R.id.pushedAtTv)
  TextView mPushedAtTextView;

  @BindString(R.string.starCountsFormat)
  String mStarCountsFormat;

  @BindString(R.string.watcherCountsFormat)
  String mWatcherCountsFormat;

  @BindString(R.string.forkCountsFormat)
  String mForkCountsFormat;

  @BindString(R.string.createdAtFormat)
  String mCreatedAtFormat;

  @BindString(R.string.updatedAtFormat)
  String mUpdatedAtFormat;

  @BindString(R.string.pushedAtForamt)
  String mPushedAtFormat;

  @BindView(R.id.readMeSv)
  ScrollView mReadMeContentsScrollView;

  @BindView(R.id.readmeFileNameTv)
  TextView mReadMeFileNameTextView;

  @BindView(R.id.markdownView)
  MarkdownView mMarkdownView;

  @BindView(R.id.progressBar)
  ProgressBar mProgressBar;

  private String mLoginName;
  private long mRepositoryId;
  private ActivityInteractor mActivityInteractor;
  private DateFormat mDateFormat;

  public static RepositoryDetailFragment newInstance(String loginName, long repositoryId) {
    RepositoryDetailFragment fragment = new RepositoryDetailFragment();

    Bundle args = new Bundle();
    args.putString("loginName", loginName);
    args.putLong("repositoryId", repositoryId);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();

    if (args != null) {
      mLoginName = args.getString("loginName");
      mRepositoryId = args.getLong("repositoryId");
    }

    mDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
        Locale.getDefault());
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivityInteractor = (ActivityInteractor) context;
  }

  @Override
  public int setLayoutId() {
    return R.layout.fragment_repository_detail;
  }

  @Override
  public void onViewBind() {

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final RepositoryDetailViewModel viewModel = ViewModelProviders.of(this)
        .get(RepositoryDetailViewModel.class);

    viewModel.findRepository(mLoginName, mRepositoryId).observe(this, new Observer<Repository>() {
      @Override
      public void onChanged(@Nullable Repository repository) {
        if (repository == null) {
          return;
        }

        DLogger.d(repository.toString());

        mLoginNameTextView.setText(repository.getOwner().getLoginName());
        mRepositoryNameTextView.setText(repository.getName());
        mRepositoryDescriptionTextView.setText(repository.getDescription());

        String language = repository.getLanguage();

        if (TextUtils.isEmpty(language)) {
          mLanguageDivider.setBackgroundColor(Color.parseColor("#000000"));
        } else {
          mLanguageDivider.setBackgroundColor(
              GithubLanguageColorMapper.getInstance().findLanguageColorByName(language));
        }

        setCountsTextView(mStarCountsTextView, mStarCountsFormat, repository.getStarCounts());
        setCountsTextView(mWatcherCountsTextView, mWatcherCountsFormat,
            repository.getWatcherCounts());
        setLicense(repository.getLicense());
        setCountsTextView(mForkCountsTextView, mForkCountsFormat, repository.getFolkCounts());
        setDateTextView(mCreatedAtTextView, mCreatedAtFormat, repository.getCreatedAt());
        setDateTextView(mUpdatedAtTextView, mUpdatedAtFormat, repository.getUpdatedAt());
        setDateTextView(mPushedAtTextView, mPushedAtFormat, repository.getPushedAt());

        viewModel.getReadMeLiveData(repository).observe(RepositoryDetailFragment.this,
            new Observer<Resource<RepositoryContentsResponse>>() {
              @Override
              public void onChanged(
                  @Nullable Resource<RepositoryContentsResponse> repositoryContentsResponseResource) {
                if (repositoryContentsResponseResource == null) {
                  return;
                }

                if (repositoryContentsResponseResource.status == Status.LOADING) {
                  mProgressBar.setVisibility(View.VISIBLE);
                } else {
                  mProgressBar.setVisibility(View.GONE);
                }

                RepositoryContentsResponse data = repositoryContentsResponseResource.data;

                if (data == null) {
                  return;
                }

                mReadMeFileNameTextView.setText(data.getName());

                viewModel.getReadMeContentsLiveData(mLoginNameTextView.getText().toString(),
                    mRepositoryNameTextView.getText().toString(), data.getName()).observe(
                    RepositoryDetailFragment.this, new Observer<Resource<String>>() {
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

                        final String data = stringResource.data;

                        if (TextUtils.isEmpty(data)) {
                          mReadMeContentsScrollView.setVisibility(View.GONE);
                          return;
                        }

                        mReadMeContentsScrollView.setVisibility(View.VISIBLE);

                        mMarkdownView.setMarkDownText(data);
                      }
                    });
              }
            });
      }
    });
  }

  private void setCountsTextView(TextView targetTextView, String format, int counts) {
    targetTextView.setText(format.replace("{C}", counts + ""));
  }

  private void setLicense(License license) {
    String licenseString = "X";

    if (license != null) {
      licenseString = license.getSpdxId();
    }

    mLicenseTextView.setText(licenseString);
  }

  private void setDateTextView(TextView targetTextView, String format, Date date) {
    targetTextView.setText(format.replace("{D}", mDateFormat.format(date)));
  }

  @OnClick(R.id.viewCodeTv)
  protected void onViewCodeClick(View view) {

  }

  @OnClick(R.id.loginNameTv)
  protected void onLoginNameClick(View view) {
    String loginName = mLoginNameTextView.getText().toString();

    if (loginName.equals(mLoginName)) {
      getActivity().onBackPressed();
    } else {
      startActivity(new Intent(getContext(), MainActivity.class)
          .putExtra("loginName", loginName));
    }
  }
}
