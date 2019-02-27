package com.example.gobear.view;

import android.content.Intent;

import com.example.domain.model.Feed;
import com.example.gobear.ui.detail.FeedDetailActivity;
import com.example.gobear.ui.main.MainActivity;
import com.example.gobear.ui.main.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Mock
    MainPresenter mockMainPresenter;

    MainActivity cut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cut = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void moveToDetail() {
        Feed expectFeed = new Feed("", "", "xxx", "");
        cut.moveToFeedDetail(expectFeed);
        Intent expectedIntent = new Intent(cut, FeedDetailActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(expectFeed.getDetailUrl(), actual.getStringExtra(FeedDetailActivity.DETAIL_URL));
    }
}
