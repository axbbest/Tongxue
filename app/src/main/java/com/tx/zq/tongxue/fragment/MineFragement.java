package com.tx.zq.tongxue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.activity.LoginActivity;
import com.tx.zq.tongxue.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/2/1.
 */
@ContentView(R.layout.minefragement)
public class MineFragement extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.feedback)
    private LinearLayout feedback;
    @ViewInject(R.id.help)
    private LinearLayout help;
    @ViewInject(R.id.introduce)
    private LinearLayout introduce;
    @ViewInject(R.id.update)
    private LinearLayout update;
    @ViewInject(R.id.clean)
    private LinearLayout clear;
    @ViewInject(R.id.out)
    private LinearLayout out;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedback.setOnClickListener(this);
        help.setOnClickListener(this);
        introduce.setOnClickListener(this);
        update.setOnClickListener(this);
        clear.setOnClickListener(this);
        out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                ToastUtils.show(getActivity(), "意见反馈");
                break;
            case R.id.help:
                ToastUtils.show(getActivity(), "帮助");
                break;
            case R.id.introduce:
                ToastUtils.show(getActivity(), "版本介绍");
                break;
            case R.id.update:
                ToastUtils.show(getActivity(), "已是最新版本");
                break;
            case R.id.clean:
                ToastUtils.show(getActivity(), "清理完毕");
                break;
            case R.id.out:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;


        }
    }
}
