package com.example.himanshu.canteen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by himanshu on 10/12/16.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<Shop> shopList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView shopCover;
        public TextView shopName;

        public MyViewHolder(View view) {
            super(view);
            shopCover = (ImageView) view.findViewById(R.id.cover);
            shopName = (TextView) view.findViewById(R.id.name);
        }
    }

    public ShopAdapter(Context mContext, List<Shop> shopList) {
        this.mContext = mContext;
        this.shopList = shopList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops, parent, false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Shop shop = shopList.get(position);
        holder.shopName.setText(String.valueOf(shop.getName()));
        Glide.with(mContext).load(shop.getThummbnail()).into(holder.shopCover);

        holder.shopCover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mContext, MenuPage.class);
                intent.putExtra("shopId", shop.getId());

                mContext.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return shopList.size();
    }
}

