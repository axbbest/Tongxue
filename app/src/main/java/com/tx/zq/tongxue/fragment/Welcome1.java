package com.tx.zq.tongxue.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tx.zq.tongxue.R;


/**
 * Created by Administrator on 2016/2/1.
 */
public class Welcome1 extends Fragment {
    String url = "";

    public Welcome1() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.mipmap.splash_bg);
        return imageView;

    }

    @SuppressLint("ValidFragment")
    public Welcome1(String url) {
        this.url = url;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
