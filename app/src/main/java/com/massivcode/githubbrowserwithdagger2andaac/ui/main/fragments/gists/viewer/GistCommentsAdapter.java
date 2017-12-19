package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.viewer;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.GistComment;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Owner;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.viewer.GistCommentsAdapter.Holder;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;
import com.massivcode.githubbrowserwithdagger2andaac.utils.time.RemainsDateTimeFormatter;
import io.realm.RealmList;

/**
 * Created by massivcode@gmail.com on 2017-12-19.
 */

public class GistCommentsAdapter extends RecyclerView.Adapter<Holder> {

  private RealmList<GistComment> mData;

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_gist_comment, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void onDataChanged(RealmList<GistComment> data) {
    mData = data;
    notifyDataSetChanged();
  }

  @Override
  public void onViewRecycled(Holder holder) {
    holder.clearImage();
    super.onViewRecycled(holder);
  }

  class Holder extends ViewHolder {

    @BindView(R.id.profileIv)
    ImageView mProfileImageView;

    @BindView(R.id.loginNameTv)
    TextView mLoginNameTextView;

    @BindView(R.id.commentedAtTv)
    TextView mCommentedAtTextView;

    @BindView(R.id.commentContentsTv)
    TextView mContentsTextView;

    @BindString(R.string.commented_format)
    String mCommentedAtFormat;

    private RemainsDateTimeFormatter mRemainsDateTimeFormatter;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      mRemainsDateTimeFormatter = RemainsDateTimeFormatter.getInstance();
    }

    void bindItem(GistComment item) {
      itemView.setTag(item);

      Owner owner = item.getUser();

      ImageLoader.loadImageAsCircleTransform(mProfileImageView, owner.getAvatarUrl());
      mLoginNameTextView.setText(owner.getLoginName());
      mContentsTextView.setText(item.getBody());
      mCommentedAtTextView.setText(
          mCommentedAtFormat.replace("{D}", mRemainsDateTimeFormatter.format(item.getCreatedAt())));
    }

    void clearImage() {
      ImageLoader.clear(mProfileImageView);
    }
  }
}
