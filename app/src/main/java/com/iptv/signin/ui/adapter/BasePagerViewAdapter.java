package com.iptv.signin.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class BasePagerViewAdapter extends PagerAdapter {
    List<ImageView> views;

    public BasePagerViewAdapter(List<ImageView> views) {
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE / 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
        ImageView imageView = views.get(position % views.size());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView(views.get(position % views.size()));
    }
}
