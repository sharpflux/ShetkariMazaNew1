package com.sharpflux.shetkarimaza.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.PostItem;
import com.sharpflux.shetkarimaza.viewholder.BaseViewHolder;

import java.util.List;


public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
  private static final int VIEW_TYPE_LOADING = 0;
  private static final int VIEW_TYPE_NORMAL = 1;
  private boolean isLoaderVisible = false;

  private List<PostItem> mPostItems;

  public PostRecyclerAdapter(List<PostItem> postItems) {
    this.mPostItems = postItems;
  }

  @NonNull
  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {
      case VIEW_TYPE_NORMAL:
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
      case VIEW_TYPE_LOADING:
        return new ProgressHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_new, parent, false));
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemViewType(int position) {
    if (isLoaderVisible) {
      return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
    } else {
      return VIEW_TYPE_NORMAL;
    }
  }

  @Override
  public int getItemCount() {
    return mPostItems == null ? 0 : mPostItems.size();
  }

  public void addItems(List<PostItem> postItems) {
    mPostItems.addAll(postItems);
    notifyDataSetChanged();
  }

  public void addLoading() {
    isLoaderVisible = true;
    mPostItems.add(new PostItem());
    notifyItemInserted(mPostItems.size() - 1);
  }

  public void removeLoading() {
    isLoaderVisible = false;
    int position = mPostItems.size() - 1;
    PostItem item = getItem(position);
    if (item != null) {
      mPostItems.remove(position);
      notifyItemRemoved(position);
    }
  }

  public void clear() {
    mPostItems.clear();
    notifyDataSetChanged();
  }

  PostItem getItem(int position) {
    return mPostItems.get(position);
  }

  public class ViewHolder extends BaseViewHolder {

    TextView textViewTitle;

    TextView textViewDescription;


    ImageView mImage;
    TextView mfullname,mAddress,mMobNo,mState,mDistrict,mTaluka,tvCompanyName,tvPriceDetails,tvRateDescription;
    String name,mobileNo,address,state,district,taluka,ImageUrl;
    LinearLayout linearRateView;


    ViewHolder(View itemView) {
      super(itemView);


      textViewTitle=itemView.findViewById(R.id.textViewTitle);
      textViewDescription=itemView.findViewById(R.id.textViewDescription);


    }

    protected void clear() {

    }

    public void onBind(int position) {
      super.onBind(position);
      PostItem item = mPostItems.get(position);

      textViewTitle.setText(item.getTitle());
      textViewDescription.setText(item.getDescription());
    }
  }

  public class ProgressHolder extends BaseViewHolder {

    ProgressBar progressBar;

    ProgressHolder(View itemView) {
      super(itemView);
      progressBar = itemView.findViewById(R.id.progressBar);
    }

    @Override
    protected void clear() {
    }
  }
}
