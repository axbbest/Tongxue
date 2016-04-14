package com.tx.zq.tongxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tx.zq.tongxue.App;
import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.activity.MainActivity;
import com.tx.zq.tongxue.activity.MainDetailsActivity;
import com.tx.zq.tongxue.adapter.MainAdapter;
import com.tx.zq.tongxue.base.AutoRVAdapter;
import com.tx.zq.tongxue.entity.People;
import com.tx.zq.tongxue.event.FirstEvent;
import com.tx.zq.tongxue.utils.ToastUtils;
import com.tx.zq.tongxue.utils.UiUtils;
import com.tx.zq.tongxue.utils.animRecycItem;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/2/1.
 */

public class MainFragement extends Fragment implements View.OnClickListener, XRecyclerView.LoadingListener, AutoRVAdapter.OnItemClickListener {
    private XRecyclerView recyclerView;
    private DbManager db;
    private ArrayList<People> all;
    private MainAdapter mainAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        x.view().inject(this, inflater, null);
        return inflater.inflate(R.layout.mainfragement, null);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mainAdapter!=null)
            mainAdapter.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = UiUtils.findView(this.getActivity(), R.id.xrecy);
        initView();
    }
    private void initView() {
        EventBus.getDefault().register(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new animRecycItem());
        recyclerView.setLoadingListener(MainFragement.this);
        recyclerView.setLoadingMoreEnabled(false);
        initDate();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Subscribe(threadMode = ThreadMode.MainThread,priority = 1)
    public void o(FirstEvent event) {
        MainActivity activity = (MainActivity) getActivity();
        activity.viewPager.setCurrentItem(0);
        activity.tabLayout.getTabAt(0).getCustomView().setSelected(true);
        activity.tabLayout.getTabAt(1).getCustomView().setSelected(false);
        initDate();
        if (!"".equals(event.getMsg()))
            Toast.makeText(getActivity(), event.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        EventBus.getDefault().unregister(this);
    }

    private void initDate() {
        try {
            db = x.getDb(((App) App.context).getDaoConfig());
            all = (ArrayList<People>) db.findAll(People.class);
            if (null == all) {
                ToastUtils.show(getActivity(), "你的同学录为空，感觉添加吧");
                return;
            }
            // ToastUtils.show(getActivity(), "sss" + all.size());
            mainAdapter = new MainAdapter(getActivity(), all);
            recyclerView.setAdapter(mainAdapter);
            mainAdapter.setOnItemClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {
        initDate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.refreshComplete();
                    }
                });

            }
        }).start();


    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void setItemClickListener(View view, ArrayList list, int position, Long id) {
        Intent i = new Intent(getActivity(), MainDetailsActivity.class);
        ArrayList<People> my = (ArrayList<People>) list;
        i.putExtra("p", my.get(position));
        startActivityForResult(i, 10);
    }
}
