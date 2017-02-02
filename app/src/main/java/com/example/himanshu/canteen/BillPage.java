package com.example.himanshu.canteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 2/2/17.
 */

public class BillPage extends AppCompatActivity {
    private List<YourOrders> yourOrdersList;
    private RecyclerView orders;
    private YourOrdersAdapter yourOrdersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_page);

        orders = (RecyclerView) findViewById(R.id.orders);
        yourOrdersList = new ArrayList<>();
        yourOrdersAdapter = new YourOrdersAdapter(yourOrdersList);

        RecyclerView.LayoutManager orderLayoutManager = new LinearLayoutManager(getApplicationContext());
        orders.setLayoutManager(orderLayoutManager);
        orders.setAdapter(yourOrdersAdapter);

        prepareBill();
    }

    private void prepareBill() {
        YourOrders yourOrders = new YourOrders("Aloo Patties", 40);
        yourOrdersList.add(yourOrders);
        YourOrders yourOrders1 = new YourOrders("Aloo Patties", 40);
        yourOrdersList.add(yourOrders1);
        YourOrders yourOrders2 = new YourOrders("Aloo Patties", 40);
        yourOrdersList.add(yourOrders2);
        YourOrders yourOrders3 = new YourOrders("Aloo Patties", 40);
        yourOrdersList.add(yourOrders3);
    }
}
