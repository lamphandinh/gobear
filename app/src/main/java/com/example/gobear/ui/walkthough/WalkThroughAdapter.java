package com.example.gobear.ui.walkthough;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gobear.R;

import java.util.ArrayList;
import java.util.List;

public class WalkThroughAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> contents = new ArrayList<>();

    public WalkThroughAdapter(Context mContext, List<String> contents) {
        this.mContext = mContext;
        this.contents.addAll(contents);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_walkthrough_item, container, false);
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_walkthrough_content);
        if (position < contents.size() && position >= 0) {
            tvContent.setText(contents.get(position));
        }
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
