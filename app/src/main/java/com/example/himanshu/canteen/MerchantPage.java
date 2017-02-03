package com.example.himanshu.canteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MerchantPage extends AppCompatActivity {
    private List<OrderToMerchant> orderList;
    private RecyclerView recyclerView;
    private OrderMerchantAdapter OrderMerchantAdapter;
    private CardView cardview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_page);

        cardview = (CardView) findViewById(R.id.card_view_merorder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_merchant);
        orderList = new ArrayList<>();
        OrderMerchantAdapter = new OrderMerchantAdapter(orderList);


        RecyclerView.LayoutManager oLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(oLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(OrderMerchantAdapter);

        prepareOrder();

    }
    private void prepareOrder() {
        OrderToMerchant order = new OrderToMerchant("2","2","60");
        orderList.add(order);
        order = new OrderToMerchant("2","2","40");
        orderList.add(order);
        order = new OrderToMerchant("2","2","220");
        orderList.add(order);

        OrderMerchantAdapter.notifyDataSetChanged();
    }

}
