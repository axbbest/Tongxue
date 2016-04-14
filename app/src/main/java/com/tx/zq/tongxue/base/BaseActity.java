package com.tx.zq.tongxue.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/3/11.
 */
public abstract class BaseActity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initDate();
    }
    protected abstract void initView(


    );

    protected abstract void initDate();
}
