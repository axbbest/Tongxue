package com.tx.zq.tongxue.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.base.AutoRVAdapter;
import com.tx.zq.tongxue.base.ViewHolder;
import com.tx.zq.tongxue.entity.People;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/12.
 */
public class MainAdapter extends AutoRVAdapter<People> {
    private final Context context;
    private final ArrayList<People> list;


    public MainAdapter(Context context, ArrayList<People> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.main;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView iv = holder.get(R.id.iv);
        ImageLoader.getInstance().displayImage("file://" + list.get(position).getPath(), iv);
        TextView tv = holder.get(R.id.tv);
        TextView time = holder.get(R.id.time);
        time.setText(System.currentTimeMillis() - list.get(position).getTime() + "");
        tv.setText(list.get(position).getName());


    }
}
