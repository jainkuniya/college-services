package com.example.himanshu.canteen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by himanshu on 12/12/16.
 */

public class MenuPage extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private List<Items> itemsList;
    private ItemsAdapter itemsAdapter;
    public static Activity MP;

    private Button billOpen;
    int shpoId = 0;
    String amulShopItems[] = {"Aloo Patties", "Masala Patties", "Cheese Patties", "Tandoori Patties", "Amul Cool", "Cheese Sandwich", "Burger", "Cream Roll", "5-Star", "Dairy Milk", "Perk", "Amul Milk", "Sprite", "Coke", "Thumbs Up"};
    int amulShopItemsPrice[] = {10, 15, 20, 25, 25, 30, 30, 10, 25, 20, 10, 18, 45, 45, 45};
    public int amulShopItemsQty[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String juiceShopItems[] = {"Banana Juice", "Pineapple Juice", "Apple Juice", "Mango Juice", "Orange Juice", "Bread", "Maggie", "BhelPuri", "Tea", "Minute Maid", "Coffee", "Papaya Juice"};
    int juiceShopItemsPrice[] = {20, 20, 20, 20, 20, 12, 22, 25, 10, 10, 15, 20};
    public int juiceShopItemsQty[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String foodBarnItems[] = {"Aloo Parantha", "Gobhi Parantha", "Muli Parantha", "Paneer Parantha", "Chilli Parantha", "Plain Roti", "Tandoori Roti", "Rumali Roti", "Palak Paneer", "Kadai Paneer", "Veg Pulao", "Chicken Pualo", "Chana Masala", "Dal Tadka"};
    int foodBarnItemsPrice[] = {30, 30, 30, 40, 50, 5, 7, 7, 140, 160, 80, 120, 120, 100};
    public int foodBarnItemsQty[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        billOpen = (Button) findViewById(R.id.billOpen);
        MP=this;

        shpoId = getIntent().getIntExtra("shopId", 0);

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        itemsList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, itemsList);

        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 2);
        recyclerView2.setLayoutManager(sLayoutManager);
        recyclerView2.setAdapter(itemsAdapter);

        prepareItems();

        billOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bill = new Intent(MenuPage.this, BillPage.class);
                startActivity(bill);
            }
        });

    }

    private void prepareItems() {
        if (shpoId == 1) {
            for (int i = 0; i < amulShopItems.length; i++) {
                Items item = new Items(i + 1, amulShopItems[i], amulShopItemsPrice[i], amulShopItemsQty[i]);
                itemsList.add(item);
            }
        } else if (shpoId == 2) {
            for (int i = 0; i < foodBarnItems.length; i++) {
                Items item = new Items(i + 1, foodBarnItems[i], foodBarnItemsPrice[i], foodBarnItemsQty[i]);
                itemsList.add(item);
            }
        } else if (shpoId == 3) {
            for (int i = 0; i < juiceShopItems.length; i++) {
                Items item = new Items(i + 1, juiceShopItems[i], juiceShopItemsPrice[i], juiceShopItemsQty[i]);
                itemsList.add(item);
            }
        }

        itemsAdapter.notifyDataSetChanged();
    }
}
