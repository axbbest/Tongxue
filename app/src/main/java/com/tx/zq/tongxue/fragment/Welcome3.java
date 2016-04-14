package com.tx.zq.tongxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.activity.LoginActivity;
import com.tx.zq.tongxue.activity.MainActivity;
import com.tx.zq.tongxue.utils.SharedPreferencesDB;
import com.tx.zq.tongxue.utils.UiUtils;

/**
 * Created by Administrator on 2016/2/1.
 */
public class Welcome3 extends Fragment implements View.OnClickListener {
    String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome3, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = UiUtils.findView(this.getActivity(), R.id.imageView);
        Button into = UiUtils.findView(this.getActivity(), R.id.into_bt);
        into.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.into_bt:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                this.getActivity().finish();
                SharedPreferencesDB.getInstance(getActivity()).setBoolean("islogin", true);

                break;
        }
    }
}
