package com.example.himanshu.canteen;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by himanshu on 9/12/16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> OrderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initial, shop, price;
        public ImageView dots;

        public MyViewHolder(View view) {
            super(view);
            initial = (TextView)view.findViewById(R.id.shop_name);
            shop = (TextView)view.findViewById(R.id.shop);
            price = (TextView)view.findViewById(R.id.price);
            dots = (ImageView)view.findViewById(R.id.overflow);
        }
    }

    public OrderAdapter(List<Order> OrderList) {
        this.OrderList=OrderList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = OrderList.get(position);
        holder.initial.setText(order.getInitial());
        holder.shop.setText(order.getShop_name());
        holder.price.setText("Total :- "+order.getTotal_price());
    }

    public int getItemCount() {
        return OrderList.size();
    }
}
