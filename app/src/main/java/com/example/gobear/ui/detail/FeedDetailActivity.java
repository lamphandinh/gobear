package com.example.gobear.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;

public class FeedDetailActivity extends BaseActivity<FeedDetailPresenter> implements IFeedDetailView {
    public static final String DETAIL_URL = "detail_url";
    private WebView wvFeedDetail;
    private FeedDetailPresenter feedDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        wvFeedDetail = (WebView) findViewById(R.id.wv_feed_detail);
        String detailUrl = getIntent().getExtras().getString(DETAIL_URL);
        loadDetail(detailUrl);
    }

    @Override
    protected FeedDetailPresenter createPresenter() {
        feedDetailPresenter = new FeedDetailPresenter(this);
        GobearApp.get().getMainComponent().inject(feedDetailPresenter);
        return feedDetailPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void loadDetail(String detailUrl) {
        wvFeedDetail.loadUrl(detailUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvFeedDetail.destroy();
        wvFeedDetail = null;
    }
}
