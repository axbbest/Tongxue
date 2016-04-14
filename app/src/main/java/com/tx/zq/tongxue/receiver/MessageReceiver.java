package com.tx.zq.tongxue.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.bmob.newim.event.MessageEvent;

/**
 * Created by Administrator on 2016/3/25.
 */
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent != null) {
            final MessageEvent event = (MessageEvent) intent.getSerializableExtra("event");
            //开发者可以在这里发应用通知
        }
    }
}