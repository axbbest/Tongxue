package com.tx.zq.tongxue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.entity.User;
import com.tx.zq.tongxue.entity.UserModel;
import com.tx.zq.tongxue.utils.SharedPreferencesDB;

public class SpashActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
    };
    Intent i;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView head = (ImageView) findViewById(R.id.head);
        Glide.with(this).load("http://img.gaoxiaogif.cn/GaoxiaoGiffiles/images/2015/06/01/beimalashidaotoushang.gif").into(head);
        context = this;
        final boolean islogin = SharedPreferencesDB.getInstance(this).getBoolean("islogin", false);

        User user = UserModel.getInstance().getCurrentUser();
        if (user != null) {
            user.toString();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!islogin) {
                    i = new Intent(context, WelcomeActivity.class);
                } else {
                    i = new Intent(context, LoginActivity.class);
                }
                startActivity(i);
                context.finish();
            }
        }, 3000);
    }
}
