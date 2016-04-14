package com.tx.zq.tongxue.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.adapter.WelcomAdapter;
import com.tx.zq.tongxue.fragment.Welcome1;
import com.tx.zq.tongxue.fragment.Welcome2;
import com.tx.zq.tongxue.fragment.Welcome3;
import com.tx.zq.tongxue.utils.UiUtils;

import java.util.ArrayList;

public class WelcomeActivity extends FragmentActivity {
    private Welcome1 welcome1;
    private Welcome2 welcome2;
    private Welcome3 welcome3;
    private ArrayList<Fragment> FragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewPager viewPager = UiUtils.findView(this, R.id.pager);
        welcome1 = new Welcome1();
        welcome2 = new Welcome2();
        welcome3 = new Welcome3();
        FragmentList.add(0, welcome1);
        FragmentList.add(1, welcome2);
        FragmentList.add(2, welcome3);
        WelcomAdapter adapter = new WelcomAdapter(getSupportFragmentManager(), FragmentList);
        viewPager.setAdapter(adapter);
    }

}
