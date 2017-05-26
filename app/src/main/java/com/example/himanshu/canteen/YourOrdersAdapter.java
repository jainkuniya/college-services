package com.example.himanshu.canteen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by himanshu on 2/2/17.
 */

public class YourOrdersAdapter extends RecyclerView.Adapter<YourOrdersAdapter.MyViewHolder> {

    private ArrayList<Items> itemsSparseArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, qty, amount;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.itemName);
            qty = (TextView) view.findViewById(R.id.totalQty);
            amount = (TextView) view.findViewById(R.id.amount);
        }
    }

    public YourOrdersAdapter(ArrayList<Items> itemsSparseArray) {
        this.itemsSparseArray = itemsSparseArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // int key = itemsSparseArray.keyAt(position);
        Items order = itemsSparseArray.get(position);


        holder.itemName.setText(order.getItemName());
        holder.qty.setText(String.valueOf(order.getItemQty()) + " * " + String.valueOf(order.getItemPrice()));
        holder.amount.setText("\u20b9" + String.valueOf(order.getItemPrice() * order.getItemQty()));
    }

    @Override
    public int getItemCount() {
        return itemsSparseArray.size();
    }

}
