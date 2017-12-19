package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.Gist;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.GistFile;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.GistsAdapter.Holder;
import com.massivcode.githubbrowserwithdagger2andaac.utils.images.ImageLoader;
import com.massivcode.githubbrowserwithdagger2andaac.utils.time.RemainsDateTimeFormatter;
import io.realm.RealmList;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-18.
 */

public class GistsAdapter extends RecyclerView.Adapter<Holder> {

  private List<Gist> mData;
  private View.OnClickListener mOnItemClickListener;

  public GistsAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }


  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gists, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override
  public void onViewRecycled(Holder holder) {
    holder.clearImage();
    super.onViewRecycled(holder);
  }

  public void onDataChanged(List<Gist> newData) {
    mData = newData;
    notifyDataSetChanged();
  }

  class Holder extends ViewHolder {

    @BindView(R.id.profileIv)
    ImageView mProfileImageView;

    @BindView(R.id.loginNameTv)
    TextView mLoginNameTextView;

    @BindView(R.id.firstFileNameTv)
    TextView mFirstFileNameTextView;

    @BindView(R.id.updatedAtTv)
    TextView mUpdatedAtTextView;

    @BindView(R.id.descriptionTv)
    TextView mDescriptionTextView;

    @BindView(R.id.fileCountsTv)
    TextView mFileCountsTextView;

    @BindView(R.id.commentCountsTv)
    TextView mCommentCountsTextView;

    @BindString(R.string.gist_updated_date_format)
    String mGistUpdatedAtFormat;

    @BindString(R.string.files_count_format)
    String mFileCountsFormat;

    @BindString(R.string.comments_count_format)
    String mCommentCountsFormat;

    private RemainsDateTimeFormatter mFormatter;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      mFormatter = RemainsDateTimeFormatter.getInstance();

      itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          Gist item = (Gist) view.getTag();

          if (item.getComments() == 0 && item.getGistComments() == null
              && item.getGistComments().size() == 0) {
            return;
          }

          mOnItemClickListener.onClick(view);
        }
      });
    }

    void bindItem(Gist item) {
      itemView.setTag(item);
      ImageLoader.loadImageAsCircleTransform(mProfileImageView, item.getOwner().getAvatarUrl());
      mLoginNameTextView.setText(item.getOwner().getLoginName());
      mDescriptionTextView.setText(item.getDescription());

      RealmList<GistFile> files = item.getFiles();

      int fileCounts = 0;

      if (files != null && !files.isEmpty()) {
        fileCounts = files.size();
        GistFile firstGistFile = files.get(0);
        mFirstFileNameTextView.setText(firstGistFile == null ? null : firstGistFile.getFileName());
      }

      setCountsTextView(mFileCountsTextView, mFileCountsFormat, fileCounts);
      setCountsTextView(mCommentCountsTextView, mCommentCountsFormat, item.getComments());
      mUpdatedAtTextView
          .setText(mGistUpdatedAtFormat.replace("{D}", mFormatter.format(item.getUpdatedAt())));
    }

    private void setCountsTextView(TextView targetTextView, String format, int counts) {
      targetTextView.setText(format.replace("{C}", counts + ""));
    }

    void clearImage() {
      ImageLoader.clear(mProfileImageView);
    }
  }
}
