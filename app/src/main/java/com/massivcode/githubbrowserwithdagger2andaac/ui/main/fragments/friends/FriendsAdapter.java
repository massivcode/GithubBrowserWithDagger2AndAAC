package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.friends;

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
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.BaseUserResponse;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.friends.FriendsAdapter.FriendsViewHolder;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-14.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

  private List<BaseUserResponse> mData;
  private View.OnClickListener mOnItemClickListener;

  public FriendsAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FriendsViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false));
  }

  @Override
  public void onBindViewHolder(FriendsViewHolder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void onDataChanged(List<BaseUserResponse> data) {
    mData = data;
    notifyDataSetChanged();
  }

  @Override
  public void onViewRecycled(FriendsViewHolder holder) {
    super.onViewRecycled(holder);
    holder.clearImage();
  }

  class FriendsViewHolder extends ViewHolder {

    @BindView(R.id.profileIv)
    ImageView mProfileImageView;

    @BindView(R.id.loginNameTv)
    TextView mLoginNameTextView;

    FriendsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(mOnItemClickListener);
    }

    void bindItem(BaseUserResponse item) {
      itemView.setTag(item);
      ImageLoader.loadImageAsCircleTransform(mProfileImageView, item.getAvatarUrl());
      mLoginNameTextView.setText(item.getLoginName());
    }

    void clearImage() {
      ImageLoader.clear(mProfileImageView);
    }
  }
}
