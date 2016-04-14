package com.tx.zq.tongxue.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/1/16.
 */
public class RVHolder  extends RecyclerView.ViewHolder {
    private ViewHolder viewHolder;
    public RVHolder(View itemView) {
        super(itemView);
        viewHolder=ViewHolder.getViewHolder(itemView);
    }
    public ViewHolder getViewHolder() {
        return viewHolder;
    }

}