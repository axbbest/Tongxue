package com.tx.zq.tongxue.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.adapter.SampleFragmentPagerAdapter;
import com.tx.zq.tongxue.entity.User;
import com.tx.zq.tongxue.fragment.ConversationFragment;
import com.tx.zq.tongxue.fragment.MainFragement;
import com.tx.zq.tongxue.fragment.MineFragement;
import com.tx.zq.tongxue.fragment.WriteFragement;
import com.tx.zq.tongxue.utils.IMMLeaks;
import com.tx.zq.tongxue.utils.Logutils;
import com.tx.zq.tongxue.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements ObseverListener {
    @ViewInject(R.id.viewPager)
    public ViewPager viewPager;
    @ViewInject(R.id.tabLayout)
    public TabLayout tabLayout;
    private MainFragement main = new MainFragement();
    private WriteFragement write = new WriteFragement();
    private MineFragement mine = new MineFragement();
    private ConversationFragment cover = new ConversationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        User user = BmobUser.getCurrentUser(this, User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logutils.e(uid + "::" + "成功");
                } else {
                    Logutils.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                ToastUtils.show(MainActivity.this, "" + status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(main);
        fragments.add(write);
        fragments.add(mine);
        fragments.add(cover);
        SampleFragmentPagerAdapter pagerAdapter =
                new SampleFragmentPagerAdapter(this, getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setVisibility(position == 1 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }
        TabLayout.Tab tabAt = tabLayout.getTabAt(0);
        tabAt.getCustomView().setSelected(true);
    }

    public void scroll(int position) {
        viewPager.setCurrentItem(0);
    }

    long exitTime;

    // 设置双击退出系统
    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        //完全退出应用时需调用clearObserver来清除观察者
        BmobNotificationManager.getInstance(this).clearObserver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BmobNotificationManager.getInstance(this).removeObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobNotificationManager.getInstance(this).addObserver(this);
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    public void onBackPressed() {
        ExitApp();
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {

    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    public void onEventMainThread(OfflineMessageEvent event) {
    }


}
