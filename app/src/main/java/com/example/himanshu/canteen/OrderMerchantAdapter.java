package com.example.himanshu.canteen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by khushboo on 29/1/17.
 */

public class OrderMerchantAdapter extends RecyclerView.Adapter<OrderMerchantAdapter.MyViewHolder> {
    private List<OrderToMerchant> OrderList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initial, shop, price;
        public ImageView dots;
        public Button done;

        public MyViewHolder(View view) {
            super(view);
            initial = (TextView)view.findViewById(R.id.shop_name);
            shop = (TextView)view.findViewById(R.id.shop);
            price = (TextView)view.findViewById(R.id.price);
            dots = (ImageView)view.findViewById(R.id.overflow);
            done=(Button)view.findViewById(R.id.doneButton);
        }

    }

    public OrderMerchantAdapter(List<OrderToMerchant> OrderList) {
        this.OrderList=OrderList;
    }
    public OrderMerchantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_merchant, parent, false);
        return new OrderMerchantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderMerchantAdapter.MyViewHolder holder, int position) {
        OrderToMerchant order = OrderList.get(position);
        holder.initial.setText(order.getInitial());
        holder.shop.setText(order.getShop_name());
        holder.price.setText("Total :- "+order.getTotal_price());
    }


    public int getItemCount() {
        return OrderList.size();
    }
}
