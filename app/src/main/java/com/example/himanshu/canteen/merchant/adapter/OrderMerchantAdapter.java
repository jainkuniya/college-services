package com.example.himanshu.canteen.merchant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.himanshu.canteen.OrderToMerchant;
import com.example.himanshu.canteen.R;

import java.util.ArrayList;

/**
 * Created by khushboo on 29/1/17.
 */

public abstract class OrderMerchantAdapter extends RecyclerView.Adapter<OrderMerchantAdapter.MyViewHolder> {
    private ArrayList<OrderToMerchant> orderList;

    public abstract void updateOrder(String id, String isVerified, String isDelivered);

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView initial, tvName, tvItems, tvTotalPrice;
        CheckBox cbVerify;
        Button done;

        MyViewHolder(View view) {
            super(view);
            initial = (TextView) view.findViewById(R.id.shop_name);
            tvName = (TextView) view.findViewById(R.id.tvCustomer);
            tvItems = (TextView) view.findViewById(R.id.tvItems);
            done = (Button) view.findViewById(R.id.doneButton);
            cbVerify = (CheckBox) view.findViewById(R.id.cbVerify);
            tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
        }

    }

    public OrderMerchantAdapter(ArrayList<OrderToMerchant> orderList) {
        this.orderList = orderList;
    }

    @Override
    public OrderMerchantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_merchant, parent, false);
        return new OrderMerchantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderMerchantAdapter.MyViewHolder holder, final int position) {
        final OrderToMerchant order = orderList.get(position);
       // holder.initial.setText(order.getName().charAt(order.getName().length()-1) + "");
        holder.initial.setText("AS");
        holder.tvName.setText(order.getName());
        holder.tvItems.setText(order.getItems());
        holder.tvTotalPrice.setText("\u20b9 " + order.getTotalPrice());
        if (!order.isVerified()) {
            holder.done.setVisibility(View.GONE);
            holder.cbVerify.setVisibility(View.VISIBLE);
        } else {
            holder.cbVerify.setVisibility(View.GONE);
            holder.done.setVisibility(View.VISIBLE);
        }

        holder.cbVerify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateOrder(order.getId(), "1", "0");
                if (isChecked) {
                    holder.cbVerify.setVisibility(View.GONE);
                    holder.done.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder(order.getId(), "1", "1");
                orderList.remove(position);

               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
