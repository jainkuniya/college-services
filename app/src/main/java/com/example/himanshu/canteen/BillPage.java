package com.example.himanshu.canteen;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.example.himanshu.canteen.R.layout.items;

/**
 * Created by himanshu on 2/2/17.
 */

public class BillPage extends AppCompatActivity {
    private RecyclerView orders;
    private YourOrdersAdapter yourOrdersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_page);

        orders = (RecyclerView) findViewById(R.id.orders);
        yourOrdersAdapter = new YourOrdersAdapter(Singleton.getInstance().getItemsSparseArray());

        RecyclerView.LayoutManager orderLayoutManager = new LinearLayoutManager(getApplicationContext());
        orders.setLayoutManager(orderLayoutManager);
        orders.setAdapter(yourOrdersAdapter);

       findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Singleton.getInstance().getItemsSparseArray().clear();;
               Intent intent = new Intent(BillPage.this, MainActivity.class);
               startActivity(intent);
               finish();
               MenuPage.MP.finish();
               MainActivity.MA.finish();
           }
       });

    }
}
