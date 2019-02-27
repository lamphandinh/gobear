package com.example.gobear.ui.main;

import com.example.domain.model.Feed;
import com.example.gobear.ui.base.IBaseView;

import java.util.List;

public interface IMainView extends IBaseView {

    void loadedFeeds(List<Feed> feeds);
    void loadedFeedsFailed(String errorMessage);
}
