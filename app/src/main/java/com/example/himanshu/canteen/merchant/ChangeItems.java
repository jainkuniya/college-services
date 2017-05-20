package com.example.himanshu.canteen.merchant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.himanshu.canteen.Items;
import com.example.himanshu.canteen.R;
import com.example.himanshu.canteen.merchant.adapter.ChangeItemsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

// Help merchant to change the items of his shop

public class ChangeItems extends AppCompatActivity {

    RecyclerView rvItems;
    Toolbar tootlbar;
    Button btAdd;
    EditText etName, etPrice;
    CheckBox cbIsAvailable;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_items);
        sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);
        tootlbar = (Toolbar) findViewById(R.id.mToolbar);
        tootlbar.setTitle("Change Items");
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.setItemAnimator(new DefaultItemAnimator());
        loadItems();

        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        cbIsAvailable = (CheckBox) findViewById(R.id.cbIsAvaible);

        btAdd = (Button) findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etName.getText().toString()) && !TextUtils.isEmpty(etPrice.getText().toString())) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myData = database.getReference("Merchant").child(sharedPref.getString("merchantID", "")).child("items");
                    long l = System.currentTimeMillis();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", etName.getText().toString());
                    map.put("price", etPrice.getText().toString());
                    map.put("isAvailable", cbIsAvailable.isChecked() ? "1" : "0");
                    myData.child(String.valueOf(l)).setValue(map);

                    etName.setText("");
                    etPrice.setText("");
                } else {
                    Snackbar.make(v, "Please enter details", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myData = database.getReference("Merchant").child(sharedPref.getString("merchantID", "")).child("items");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Items> itemses = new ArrayList<Items>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Items items = new Items(String.valueOf(postSnapshot.getKey()),
                                String.valueOf(postSnapshot.child("name").getValue()),
                                Integer.parseInt(String.valueOf(postSnapshot.child("price").getValue())),
                                String.valueOf(postSnapshot.child("isAvailable").getValue()).equals("1"));
                        itemses.add(items);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                ChangeItemsAdapter adapter = new ChangeItemsAdapter(itemses) {
                    @Override
                    public void updateItem(Items items) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myData = database.getReference("Merchant").child(sharedPref.getString("merchantID", "")).child("items");
                        String l = items.getId();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", items.getItemName());
                        map.put("price",String.valueOf(items.getItemPrice()));
                        map.put("isAvailable", items.isAvailable() ? "1" : "0");
                        myData.child(String.valueOf(l)).setValue(map);
                    }
                };
                rvItems.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
