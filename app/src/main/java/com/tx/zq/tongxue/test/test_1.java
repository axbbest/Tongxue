package com.tx.zq.tongxue.test;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.xutils.common.util.LogUtil;

/**
 * Created by Administrator on 2016/3/30.
 */
public class test_1 extends ApplicationTestCase<Application> {
    final String TAG = "ApplicationTest";

    public test_1() {
        super(Application.class);

    }

    public test_1(Class<Application> applicationClass) {
        super(applicationClass);
    }


    public void tests() throws Exception {
        int expected = 1;
        int reality = 1;
        LogUtil.e("nihao");

    }


}
