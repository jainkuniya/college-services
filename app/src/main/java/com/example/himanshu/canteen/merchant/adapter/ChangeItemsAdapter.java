package com.example.himanshu.canteen.merchant.adapter;


import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.himanshu.canteen.Items;
import com.example.himanshu.canteen.R;

import java.util.ArrayList;

/**
 * Created by vishwesh3 on 11/4/17.
 */


public abstract class ChangeItemsAdapter extends RecyclerView.Adapter<ChangeItemsAdapter.MyViewHolder> {

    private ArrayList<Items> itemsArrayList;

    public abstract void updateItem(Items items);

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText etName, etPrice;
        public CheckBox cbIsAvailable;

        public MyViewHolder(View view) {
            super(view);
            etName = (EditText) view.findViewById(R.id.etName);
            etPrice = (EditText) view.findViewById(R.id.etPrice);
            cbIsAvailable = (CheckBox) view.findViewById(R.id.cbIsAvaible);
        }
    }


    public ChangeItemsAdapter(ArrayList<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_change_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Items item = itemsArrayList.get(position);
        holder.etName.setText(item.getItemName());
        holder.etPrice.setText(item.getItemPrice()+"");
        holder.cbIsAvailable.setChecked(item.isAvailable());

        holder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemsArrayList.get(position).setItemName(holder.etName.getText().toString());
                updateItem(itemsArrayList.get(position));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.etPrice.getText().toString().length()>=1) {
                    itemsArrayList.get(position).setItemPrice(Integer.parseInt(holder.etPrice.getText().toString()));
                    updateItem(itemsArrayList.get(position));
                }
                else {
                    itemsArrayList.get(position).setItemPrice(0);
                    updateItem(itemsArrayList.get(position));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.cbIsAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemsArrayList.get(position).setIsAvailable(holder.cbIsAvailable.isChecked());
                updateItem(itemsArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }
}