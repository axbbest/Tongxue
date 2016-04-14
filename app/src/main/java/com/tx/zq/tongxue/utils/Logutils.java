package com.tx.zq.tongxue.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.util.Log;

import com.tx.zq.tongxue.config.Config;


public class Logutils {
    private static final String b = "com.ht.app";

    public static void a(String content) {
        if (Config.isOpen)
            Log.d(b, content);
    }
    public static void e(String content) {
        if (Config.isOpen)
        Log.e(b, content);
    }


}
