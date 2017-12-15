package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.explorer;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.explorer.RepositoryPathAdapter.Holder;
import java.util.Stack;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryPathAdapter extends RecyclerView.Adapter<Holder> {

  private Stack<String> mData;
  private View.OnClickListener mOnItemClickListener;

  public RepositoryPathAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_repository_path, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.bindItem(mData.get(position), isLastItem(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  private boolean isLastItem(int position) {
    int itemCount = getItemCount();

    return itemCount == 0 || (itemCount - 1) == position;
  }

  public void onDataChanged(Stack<String> newData) {
    mData = newData;
    notifyDataSetChanged();
  }

  class Holder extends ViewHolder {

    @BindView(R.id.pathTv)
    TextView mPathNameTextView;

    @BindView(R.id.hasMoreNextIv)
    ImageView mHasMoreImageView;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          int position = getAdapterPosition();

          if (isLastItem(position)) {
            return;
          }

          mOnItemClickListener.onClick(view);
        }
      });
    }

    void bindItem(String item, boolean isLastItem) {
      itemView.setTag(item);

      if (isLastItem) {
        mPathNameTextView.setTextColor(Color.parseColor("#000000"));
        mHasMoreImageView.setVisibility(View.GONE);
      } else {
        mPathNameTextView.setTextColor(Color.parseColor("#2196f3"));
        mHasMoreImageView.setVisibility(View.VISIBLE);
      }

      mPathNameTextView.setText(item);
    }
  }

}
