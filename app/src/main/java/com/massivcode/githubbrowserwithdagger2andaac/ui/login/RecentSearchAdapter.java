package com.massivcode.githubbrowserwithdagger2andaac.ui.login;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.SearchSuggestion;
import com.massivcode.githubbrowserwithdagger2andaac.ui.login.RecentSearchAdapter.RecentSearchViewHolder;
import io.realm.RealmResults;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by massivcode@gmail.com on 2017. 12. 13. 10:52
 */

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchViewHolder> {

  private RealmResults<SearchSuggestion> mData;
  private View.OnClickListener mOnItemClickListener;

  public RecentSearchAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public RecentSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RecentSearchViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_recent_searched, parent, false));
  }

  @Override
  public void onBindViewHolder(RecentSearchViewHolder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size() > 5 ? 5 : mData.size();
  }

  public void onDataChanged(RealmResults<SearchSuggestion> newData) {
    mData = newData;
    notifyDataSetChanged();
  }

  class RecentSearchViewHolder extends ViewHolder {

    @BindView(R.id.keywordTv)
    TextView mKeywordTextView;

    @BindView(R.id.searchedAtTv)
    TextView mSearchedAtTextView;

    private DateFormat mDateFormat;

    RecentSearchViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      mDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
          Locale.getDefault());

      itemView.setOnClickListener(mOnItemClickListener);
    }

    void bindItem(SearchSuggestion item) {
      itemView.setTag(item.getKeyword());
      mKeywordTextView.setText(item.getKeyword());
      mSearchedAtTextView.setText(mDateFormat.format(item.getSearchedAt()));
    }
  }
}
