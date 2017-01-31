package com.example.himanshu.canteen;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by himanshu on 12/12/16.
 */

public class AmulShop extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private List<Items> itemsList;
    private ItemsAdapter itemsAdapter;
    /*GridView gridView;

    String itemName[] = {
            "Patties",
            "Burger",
            "Cream Roll",
            "Ice Cream",
            "Curd",
            "Milk",
            "Minute Maid",
            "Patties",
            "Burger",
            "Cream Roll",
            "Ice Cream",
            "Curd",
            "Milk",
            "Patties",
            "Burger",
            "Cream Roll",
            "Ice Cream",
            "Curd",
            "Milk",
            "Patties",
            "Burger",
            "Cream Roll",
            "Ice Cream",
            "Curd",
            "Milk"
    };

    String itemPrice[] = {
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30",
            "Rs 20",
            "Rs 30"
    };

    int itemQty[] = {
            0,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,5
    };*/

    int shpoId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        shpoId = getIntent().getIntExtra("shopId", 0);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        itemsList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, itemsList);

        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 2);
        recyclerView2.setLayoutManager(sLayoutManager);
        recyclerView2.setAdapter(itemsAdapter);

        prepareItems();
    }

    private void prepareItems() {
        for (int i = 0; i < shpoId + 5; i++) {
            Items item13 = new Items(14, "Patties", 20 + i + shpoId, 0);
            itemsList.add(item13);
        }

        itemsAdapter.notifyDataSetChanged();
    }
}
