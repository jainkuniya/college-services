package com.example.himanshu.canteen;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
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
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<Shop> shopList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView shopCover;
        public TextView shopName;

        public MyViewHolder(View view) {
            super(view);
            shopCover = (ImageView)view.findViewById(R.id.cover);
            shopName = (TextView)view.findViewById(R.id.name);
        }

    }

    public ShopAdapter(Context mContext, List<Shop> shopList) {
        this.mContext=mContext;
        this.shopList=shopList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops, parent, false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder (final MyViewHolder holder, int position) {
        final Shop shop = shopList.get(position);
        holder.shopName.setText(shop.getName());
        Glide.with(mContext).load(shop.getThummbnail()).into(holder.shopCover);

        holder.shopCover.setOnClickListener(new View.OnClickListener() {
            int i;
            public void onClick(View v) {
                i=shop.getId();
                //activity(i);
                Intent intent =  new Intent();
                if(i==1)
                    intent = new Intent(mContext, Amul_Shop.class);
                else if(i==2)
                    intent = new Intent(mContext, Food_Barn.class);
                else if(i==3)
                    intent = new Intent(mContext, Juice_Shop.class);
                else if(i==4)
                    intent = new Intent(mContext, Laundry.class);
                else if(i==5)
                    intent = new Intent(mContext, Pal.class);
                mContext.startActivity(intent);
            }
        });
    }

    //public void activity(int num) {
        /*Intent intent =  new Intent();
        if(num==1)
            intent = new Intent(mContext, Amul_Shop.class);
        else if(num==2)
            intent = new Intent(mContext, Food_Barn.class);
        else if(num==3)
            intent = new Intent(mContext, Juice_Shop.class);
        else if(num==4)
            intent = new Intent(mContext, Laundry.class);
        else if(num==5)
            intent = new Intent(mContext, Pal.class);
        mContext.startActivity(intent);
    }*/

    public int getItemCount() {
        return shopList.size();
    }
}

