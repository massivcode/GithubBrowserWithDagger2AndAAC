package com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.viewer;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.githubbrowserwithdagger2andaac.R;
import com.massivcode.githubbrowserwithdagger2andaac.models.local.GistFile;
import com.massivcode.githubbrowserwithdagger2andaac.services.RawContentService;
import com.massivcode.githubbrowserwithdagger2andaac.ui.main.fragments.gists.viewer.GistFilesAdapter.Holder;
import com.massivcode.githubbrowserwithdagger2andaac.utils.network.NetworkModule;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by massivcode@gmail.com on 2017-12-19.
 */

public class GistFilesAdapter extends RecyclerView.Adapter<Holder> {

  private RealmList<GistFile> mData;

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_gist_content, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.bindItem(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void onDataChanged(RealmList<GistFile> data) {
    mData = data;
    notifyDataSetChanged();
  }

  class Holder extends ViewHolder {

    @BindView(R.id.fileNameTv)
    TextView mFileNameTextView;

    @BindView(R.id.fileContentsTv)
    TextView mFileContentsTextView;

    private RawContentService mRawContentService;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      mRawContentService = NetworkModule.getInstance().provideRawContentService();
    }

    void bindItem(GistFile item) {
      itemView.setTag(item);

      mFileNameTextView.setText(item.getFileName());
      setFileContents(item.getRawUrl());
    }

    private void setFileContents(String url) {
      String identifier = url.replace(RawContentService.SERVER_URL, "");
      System.out.println(identifier);

      mRawContentService.fetchGistContent(identifier).enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
          mFileContentsTextView.setText(response.body());
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
      });
    }

  }
}
