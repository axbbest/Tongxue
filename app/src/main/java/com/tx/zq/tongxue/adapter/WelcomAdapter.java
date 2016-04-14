package com.tx.zq.tongxue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/1.
 */
public class WelcomAdapter extends FragmentPagerAdapter {
    private final FragmentManager fm;
    private final ArrayList<Fragment> list;

    public WelcomAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.fm = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
