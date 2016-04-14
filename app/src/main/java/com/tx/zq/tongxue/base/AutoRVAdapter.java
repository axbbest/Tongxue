package com.tx.zq.tongxue.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/16.
 */
public abstract class AutoRVAdapter<Data> extends RecyclerView.Adapter<RVHolder> {
    public ArrayList<Data> list;
    private Context context;
    public AutoRVAdapter(Context context, ArrayList<Data> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onCreateViewLayoutID(viewType), null);
        return new RVHolder(view);
    }

    public abstract int onCreateViewLayoutID(int viewType);


    @Override
    public void onViewRecycled(final RVHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, final int position) {
        onBindViewHolder(holder.getViewHolder(), position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.setItemClickListener(v,list,position , holder.getItemId());
                }
            });
        }
    }
    public abstract void onBindViewHolder(ViewHolder holder, int position);
    @Override
    public int getItemCount() {
        return list.size();
    }

    private OnItemClickListener onItemClickListener;
public interface  OnItemClickListener{

    void setItemClickListener(View view, ArrayList list, int position, Long id);
}
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}