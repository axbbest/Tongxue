package com.tx.zq.tongxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.activity.ChatActivity;
import com.tx.zq.tongxue.adapter.ConversationAdapter;
import com.tx.zq.tongxue.adapter.OnRecyclerViewListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/2/1.
 */


@ContentView(R.layout.fragment_conversation)
public class ConversationFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.rc_view)
    private RecyclerView rc_view;
    @ViewInject(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    private ConversationAdapter adapter;
    private LinearLayoutManager layoutManager;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = x.view().inject(this, inflater, null);
        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        adapter = new ConversationAdapter();
        rc_view.setAdapter(adapter);
        setListener();
    }

    private void setListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                BmobIMConversation c = adapter.getItem(position);
                bundle.putSerializable("c", c);
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }

            @Override
            public boolean onItemLongClick(int position) {
                //以下两种方式均可以删除会话
//                BmobIM.getInstance().deleteConversation(adapter.getItem(position).getConversationId());
                BmobIM.getInstance().deleteConversation(adapter.getItem(position));
                adapter.remove(position);
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        sw_refresh.setRefreshing(true);
        query();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * 注册消息接收事件
     *
     * @param event 1、与用户相关的由开发者自己维护，SDK内部只存储用户信息
     *              2、开发者获取到信息后，可调用SDK内部提供的方法更新会话
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(MessageEvent event) {
        //重新获取本地消息并刷新列表
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(OfflineMessageEvent event) {
        //重新刷新列表
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
    }

    /**
     * 查询本地会话
     */
    public void query() {
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
        sw_refresh.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
