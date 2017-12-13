package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview;

import android.support.v4.content.ContextCompat;
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
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.overview.OverviewMenuAdapter.OverviewMenuViewHolder;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017. 12. 13. 13:36
 */

public class OverviewMenuAdapter extends RecyclerView.Adapter<OverviewMenuViewHolder> {

  private List<OverviewMenuItem> mData;
  private View.OnClickListener mOnItemClickListener;

  public OverviewMenuAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public OverviewMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new OverviewMenuViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_overview_menu, parent, false));
  }

  @Override
  public void onBindViewHolder(OverviewMenuViewHolder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void onDataChanged(List<OverviewMenuItem> newData) {
    mData = newData;
    notifyDataSetChanged();
  }

  class OverviewMenuViewHolder extends ViewHolder {

    @BindView(R.id.menuTv)
    TextView mMenuTextView;

    @BindView(R.id.countsTv)
    TextView mCountsTextView;

    OverviewMenuViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(mOnItemClickListener);
    }

    void bindItem(OverviewMenuItem item) {
      itemView.setTag(item);

      mMenuTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
          ContextCompat.getDrawable(itemView.getContext(), item.getIconId()),
          null, null, null);
      mMenuTextView.setText(item.getTitleId());
      mCountsTextView.setText(item.getCounts() + "");
    }
  }
}
