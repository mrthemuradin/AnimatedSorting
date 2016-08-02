package com.example.mrmuradin.animationsorting;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    List<String> dataList;

    public ListAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvListItem.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvListItem;
        FrameLayout flContainer;

        public ViewHolder(View v) {
            super(v);
            tvListItem = (TextView) v.findViewById(R.id.tv_ListItem);
//            flContainer = (FrameLayout) v.findViewById(R.id.fl_Container);
        }

    }


}
