package com.example.himanshu.canteen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.himanshu.canteen.R.layout.items;


/**
 * Created by himanshu on 13/1/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Items> itemsList;

    public ItemsAdapter(Context mContext, List<Items> itemsList) {
        this.mContext = mContext;
        this.itemsList = itemsList;
    }

    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(items, parent, false);
        return new ItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemsAdapter.MyViewHolder holder, final int position) {
        final Items item = itemsList.get(position);
        int quantity = Integer.parseInt(String.valueOf(item.getItemQty()));
        holder.nameItem.setText(item.getItemName());
        holder.priceItem.setText("\u20b9 " + String.valueOf(item.getItemPrice()));
        holder.qtyItem.setText(String.valueOf(quantity));
        if (item.getItemQty() == 0) {
            holder.relativeLayout.setBackgroundColor(Color.WHITE);
        } else {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#AED581"));
        }
        final MenuPage a = new MenuPage();
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.qtyItem.getText().toString());
                q = q + 1;
                holder.qtyItem.setText(String.valueOf(q));
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#AED581"));
                itemsList.get(position).setItemQty(q);
                //a.amulShopItemsQty[position]=q;
                Singleton.getInstance().getItemsSparseArray().append(item.getS_no(),item);
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.qtyItem.getText().toString());
                if (q > 0) {
                    q = q - 1;
                    holder.qtyItem.setText(String.valueOf(q));
                    a.amulShopItemsQty[position]=q;
                }
                itemsList.get(position).setItemQty(q);
                if (q == 0) {
                    holder.relativeLayout.setBackgroundColor(Color.WHITE);
                    Singleton.getInstance().getItemsSparseArray().remove(item.getS_no());
                }else
                {
                    Singleton.getInstance().getItemsSparseArray().append(item.getS_no(),item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameItem, priceItem, qtyItem;
        public Button increment, decrement;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            nameItem = (TextView) view.findViewById(R.id.item_name);
            priceItem = (TextView) view.findViewById(R.id.item_price);
            qtyItem = (TextView) view.findViewById(R.id.qty);
            increment = (Button) view.findViewById(R.id.increase);
            decrement = (Button) view.findViewById(R.id.decrease);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.rl1);
        }
    }


}
