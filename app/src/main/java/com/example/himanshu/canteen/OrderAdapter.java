package com.example.himanshu.canteen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by himanshu on 9/12/16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private ArrayList<OrderToMerchant> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initial, shop, price, tvItems, tvStatus;

        public MyViewHolder(View view) {
            super(view);
            initial = (TextView) view.findViewById(R.id.shop_name);
            shop = (TextView) view.findViewById(R.id.shop);
            price = (TextView) view.findViewById(R.id.price);
            tvItems = (TextView) view.findViewById(R.id.tvItems);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        }
    }

    public OrderAdapter(ArrayList<OrderToMerchant> OrderList) {
        this.orderList = OrderList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderToMerchant order = orderList.get(position);
        holder.initial.setText(order.getName().charAt(order.getName().length() - 1) + "");
        holder.shop.setText(order.getName());
        holder.price.setText("Total :- " + order.getTotalPrice());
        holder.tvItems.setText(order.getItems());
        if (order.isDelivered()) {
            holder.tvStatus.setText("Ready");
        } else if (order.isVerified()) {
            holder.tvStatus.setText("Verified");
        } else {
            holder.tvStatus.setText("Verification Pending");
        }
    }

    public int getItemCount() {
        return orderList.size();
    }
}
