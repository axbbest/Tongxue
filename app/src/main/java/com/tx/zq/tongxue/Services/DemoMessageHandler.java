package com.tx.zq.tongxue.Services;

import android.content.Context;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by Administrator on 2016/3/25.
 */
public class DemoMessageHandler extends BmobIMMessageHandler {

    private Context context;

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
    }

    public DemoMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}