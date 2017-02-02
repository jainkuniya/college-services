package com.example.himanshu.canteen;

import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by himanshu on 2/2/17.
 */

public class YourOrdersAdapter extends RecyclerView.Adapter<YourOrdersAdapter.MyViewHolder> {

    private List<YourOrders> yourOrdersList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, totalRupees;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.itemName);
            totalRupees = (TextView) view.findViewById(R.id.totalRupees);
        }
    }

    public YourOrdersAdapter(List<YourOrders> yourOrdersList) {
        this.yourOrdersList=yourOrdersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YourOrders order = yourOrdersList.get(position);
        holder.itemName.setText(order.getItemName());
        holder.totalRupees.setText("\u20b9 " + String.valueOf(order.getTotalRupees()));
    }

    @Override
    public int getItemCount() {
        return yourOrdersList.size();
    }
}
