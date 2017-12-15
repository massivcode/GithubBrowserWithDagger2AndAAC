package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.contents;

import android.graphics.drawable.Drawable;
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
import com.massivcode.githubbrowserwithdagger2andaac.models.remote.RepositoryContentsResponse;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.repository.contents.RepositoryContentsAdapter.Holder;
import java.util.List;

/**
 * Created by massivcode@gmail.com on 2017-12-15.
 */

public class RepositoryContentsAdapter extends RecyclerView.Adapter<Holder> {

  private List<RepositoryContentsResponse> mData;
  private View.OnClickListener mOnItemClickListener;

  public RepositoryContentsAdapter(OnClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_repository_contents, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void onDataChanged(List<RepositoryContentsResponse> newData) {
    mData = newData;
    notifyDataSetChanged();
  }

  class Holder extends ViewHolder {

    @BindView(R.id.fileInfoTv)
    TextView mFileInfoTextView;

    @BindView(R.id.fileSizeTv)
    TextView mFileSizeTextView;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(mOnItemClickListener);
    }

    void bindItem(RepositoryContentsResponse item) {
      itemView.setTag(item);
      setFileInfo(item.getName(), item.getType());
      mFileSizeTextView.setText(item.getSize() + " bytes");
    }

    private void setFileInfo(String fileName, String fileType) {
      boolean isFile = fileType.equals("file");

      Drawable startDrawable = ContextCompat
          .getDrawable(itemView.getContext().getApplicationContext(),
              isFile ? R.drawable.ic_file : R.drawable.ic_folder);

      mFileInfoTextView
          .setCompoundDrawablesWithIntrinsicBounds(startDrawable, null, null, null);

      mFileInfoTextView.setText(fileName);
    }
  }
}
