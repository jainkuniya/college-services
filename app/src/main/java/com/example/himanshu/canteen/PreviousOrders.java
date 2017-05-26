package com.example.himanshu.canteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Provide list of previous orders to the user.

public class PreviousOrders extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_order);

        Toolbar tootlbar = (Toolbar) findViewById(R.id.mToolbar);
        tootlbar.setTitle("Previous Orders");

        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager oLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(oLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareOrder();
    }

    private void prepareOrder() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myData = database.getReference("Client").child(sharedPref.getString("userRollNo", "")).child("Orders");
        myData.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<OrderToMerchant> orderList = new ArrayList<OrderToMerchant>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DatabaseReference databaseReference = database.getReference("Orders").child(String.valueOf(postSnapshot.getValue()));
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (String.valueOf(dataSnapshot.child("IsOrderDelievered").getValue()).equals("0")) {
                                String items = "";
                                Iterable<DataSnapshot> iterable = dataSnapshot.child("Items").getChildren();
                                for (DataSnapshot dataSnapshot1 : iterable) {
                                    items += dataSnapshot1.getKey() + " - " + (String.valueOf(dataSnapshot1.getValue())) + ", ";
                                }
                                OrderToMerchant orderToMerchant = new OrderToMerchant(dataSnapshot.getKey(),
                                        String.valueOf(dataSnapshot.child("ShopID").getValue()),
                                        items,
                                        String.valueOf(dataSnapshot.child("Total Price").getValue())
                                        , String.valueOf(dataSnapshot.child("IsOrderConfirmed").getValue()).equals("1"),
                                        false);

                                orderList.add(orderToMerchant);
                                OrderAdapter orderMerchantAdapter = new OrderAdapter(orderList);
                                recyclerView.setAdapter(orderMerchantAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                OrderAdapter orderMerchantAdapter = new OrderAdapter(orderList);
                recyclerView.setAdapter(orderMerchantAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
