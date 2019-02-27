package com.example.gobear.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.domain.model.Feed;
import com.example.gobear.R;

public class FeedAdapter extends ListAdapter<Feed, FeedAdapter.FeedViewHolder> {

    private static DiffUtil.ItemCallback<Feed> DIFF_CALLBACK = new DiffUtil.ItemCallback<Feed>() {
        @Override
        public boolean areItemsTheSame(Feed oldItem, Feed newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(Feed oldItem, Feed newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    private View.OnClickListener itemClickListener;

    public FeedAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_feed, parent, false);
        FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        feedViewHolder.setViewClickListener(itemClickListener);
        return feedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Feed feed = getItem(position);
        if (feed != null) {
            holder.title.setText(feed.getTitle());
            holder.description.setText(feed.getDescription());
            Glide.with(holder.itemView.getContext())
                    .load(feed.getThumbnailUrl())
                    .centerCrop()
                    .into(holder.thumbnail);
        }

    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView description;
        public FeedViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.imv_feed_thumbnail);
            title = (TextView) itemView.findViewById(R.id.tv_feed_title);
            description = (TextView) itemView.findViewById(R.id.tv_feed_description);
        }

        public void setViewClickListener(View.OnClickListener viewClickListener) {
            itemView.setTag(this);
            itemView.setOnClickListener(viewClickListener);
        }
    }
}
