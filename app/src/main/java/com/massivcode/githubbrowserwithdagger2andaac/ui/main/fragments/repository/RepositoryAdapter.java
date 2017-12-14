package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Repository;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.RepositoryAdapter.RepositoryViewHolder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

  public interface FilterResultsCallback {

    void onFilterCompleted(List<Repository> filtered, String keyword);
  }

  private List<Repository> mData;
  private List<Repository> mFiltered;
  private boolean mIsFilterMode = false;
  private View.OnClickListener mOnItemClickListener;

  public RepositoryAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RepositoryViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false));
  }

  @Override
  public void onBindViewHolder(RepositoryViewHolder holder, int position) {
    holder.bindItem(getItem(position));
  }

  @Override
  public int getItemCount() {
    if (mIsFilterMode) {
      return mFiltered == null ? 0 : mFiltered.size();
    } else {
      return mData == null ? 0 : mData.size();
    }
  }

  public Repository getItem(int position) {
    return mIsFilterMode ? mFiltered.get(position) : mData.get(position);
  }

  public void onDataChanged(List<Repository> data) {
    if (mIsFilterMode) {
      mFiltered = data;
    } else {
      mData = data;
    }

    notifyDataSetChanged();
  }

  public void setFilterMode(boolean isFilterMode) {
    mIsFilterMode = isFilterMode;
    notifyDataSetChanged();
  }

  public void onFiltered(String keyword, FilterResultsCallback callback) {
    if (mData == null || mData.size() == 0) {
      return;
    }

    mIsFilterMode = true;

    List<Repository> filtered = new ArrayList<>();

    for (Repository each : mData) {
      if (each.getName().contains(keyword)) {
        filtered.add(each);
      }
    }

    callback.onFilterCompleted(filtered, keyword);
  }

  class RepositoryViewHolder extends ViewHolder {

    @BindView(R.id.repositoryNameTv)
    TextView mRepositoryNameTextView;

    @BindView(R.id.repositoryDescriptionTv)
    TextView mDescriptionTextView;

    @BindView(R.id.languageTv)
    TextView mLanguageTextView;

    @BindView(R.id.licenseTv)
    TextView mLicenseTextView;

    @BindView(R.id.dateTv)
    TextView mDateTextView;

    private DateFormat mDateFormat;

    RepositoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(mOnItemClickListener);

      mDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
          Locale.getDefault());
    }

    void bindItem(Repository item) {
      itemView.setTag(item);
      setTextViewState(mRepositoryNameTextView, item.getName());
      setTextViewState(mDescriptionTextView, item.getDescription());
      setTextViewState(mLanguageTextView, item.getLanguage());
      setTextViewState(mLicenseTextView,
          item.getLicense() == null ? null : item.getLicense().getSpdxId());
      setTextViewState(mDateTextView, mDateFormat.format(item.getUpdatedAt()));
    }

    private void setTextViewState(TextView targetTextView, String text) {
      if (TextUtils.isEmpty(text)) {
        targetTextView.setVisibility(View.GONE);
      } else {
        targetTextView.setVisibility(View.VISIBLE);
        targetTextView.setText(text);
      }
    }

  }
}
