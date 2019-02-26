package com.example.gobear.ui.walkthough;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;
import com.example.gobear.ui.base.BasePresenter;
import com.example.gobear.ui.custom.CircleIndicatorView;
import com.example.gobear.ui.login.LoginActivity;

import java.util.Arrays;
import java.util.List;

public class WalkThroughActivity extends BaseActivity {

    private CircleIndicatorView circleIndicatorView;
    private ViewPager vpOnboarderPager;
    private WalkThroughAdapter walkThroughAdapter;
    private TextView btnSkip;

    private static final List<String> WalkthroughPages = Arrays.asList("Step 1", "Step 2", "Step 3");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        btnSkip = (TextView) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipWalkthrough();
            }
        });
        circleIndicatorView = (CircleIndicatorView) findViewById(R.id.circle_indicator_view);
        circleIndicatorView.setPageIndicators(WalkthroughPages.size());

        vpOnboarderPager = (ViewPager) findViewById(R.id.vp_pager);
        vpOnboarderPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                circleIndicatorView.setCurrentPage(position);
                if (position == WalkthroughPages.size() - 1) {
                    btnSkip.setText(getString(R.string._continue));
                } else {
                    btnSkip.setText(getString(R.string.skip));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        walkThroughAdapter = new WalkThroughAdapter(this, WalkthroughPages);
        vpOnboarderPager.setAdapter(walkThroughAdapter);
    }

    private void skipWalkthrough() {
        Intent goLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(goLoginIntent);
        finish();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
