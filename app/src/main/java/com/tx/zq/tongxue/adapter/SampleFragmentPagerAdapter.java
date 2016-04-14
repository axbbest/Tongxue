package com.tx.zq.tongxue.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tx.zq.tongxue.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/12.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private String tabTitles[] = new String[]{"主页", "友谊", "我的", "会话"};
    private int imageResId[] = new int[]{R.drawable.homemenu, R.drawable.homemenu2, R.drawable.homemenu3, R.drawable.homemenu2};

    public SampleFragmentPagerAdapter(Context context, FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fm = fm;
        this.fragments = fragments;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textview);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.imageview);
        img.setImageResource(imageResId[position]);
        return v;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}