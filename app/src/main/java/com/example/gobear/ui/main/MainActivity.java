package com.example.gobear.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.example.domain.model.Feed;
import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;
import com.example.gobear.ui.detail.FeedDetailActivity;
import com.example.gobear.util.UIUtils;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    private SwipeRefreshLayout feedPullToRefresh;
    private RecyclerView rvFeeds;
    private FeedAdapter feedAdapter;
    private MainPresenter mainPresenter;
    private View.OnClickListener feedItemClickListener;
    private List<Feed> curFeedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        feedPullToRefresh = (SwipeRefreshLayout) findViewById(R.id.feed_pull_to_refresh);
        feedPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFeeds();
            }
        });
        rvFeeds = (RecyclerView) findViewById(R.id.rv_feeds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFeeds.setLayoutManager(linearLayoutManager);
        rvFeeds.setHasFixedSize(true);
        feedAdapter = new FeedAdapter();
        feedItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)view.getTag();
                int position = viewHolder.getAdapterPosition();
                if (curFeedList != null && position >=0 && position < curFeedList.size()) {
                    moveToFeedDetail(curFeedList.get(position));
                }
            }
        };
        feedAdapter.setItemClickListener(feedItemClickListener);
        rvFeeds.setAdapter(feedAdapter);
        mainPresenter.loadFeeds();
    }

    @Override
    protected MainPresenter createPresenter() {
        mainPresenter = new MainPresenter(this);
        GobearApp.get().getMainComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedItemClickListener = null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadedFeeds(List<Feed> feeds) {
        curFeedList = feeds;
        feedAdapter.submitList(curFeedList);
        feedPullToRefresh.setRefreshing(false);
    }

    @Override
    public void loadedFeedsFailed(String errorMessage) {
        feedPullToRefresh.setRefreshing(false);
        UIUtils.showShortToast(errorMessage);
    }

    public void moveToFeedDetail(Feed feed) {
        Intent feedDetailIntent = new Intent(this, FeedDetailActivity.class);
        feedDetailIntent.putExtra(FeedDetailActivity.DETAIL_URL, feed.getDetailUrl());
        startActivity(feedDetailIntent);
    }
}
