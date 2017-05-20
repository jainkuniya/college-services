package com.example.himanshu.canteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Shop> shopList;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private View navHeader;
    private TextView txtName, nameInitial, txtRollno;
    private String clientName, clientRollno, clienInitials;
    public static int navItemIndex = 0;
    private String[] activityTitles;
    private Toolbar mToolbar;

    private RecyclerView  recyclerView1;

    private OrderAdapter orderAdapter;
    public static Activity MA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        MA = this;

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtRollno = (TextView) navHeader.findViewById(R.id.rollno);
        nameInitial = (TextView) navHeader.findViewById(R.id.name_initial);
        activityTitles = getResources().getStringArray(R.array.user_side_item_titles);

        loadNavHeader();
        loadHomeFragment();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            loadHomeFragment();
        }

        setUpNavigationView();

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        shopList = new ArrayList<>();

        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(sLayoutManager);

        prepareShops();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void loadNavHeader() {

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        clientName = sharedPreferences.getString("userName", "");
        clientRollno = sharedPreferences.getString("userRollNo", "");
        clienInitials = sharedPreferences.getString("userInitials", "");
        txtName.setText("" + clientName);
        txtRollno.setText("" + clientRollno);
        nameInitial.setText("" + clienInitials);
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Home");
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main.xml content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        getSupportActionBar().setTitle("Home");
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_previousOrders:
                        navItemIndex = 1;
                        startActivity(new Intent(MainActivity.this, PreviousOrders.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 2;
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userRollNo", "");
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        drawer.closeDrawers();
                        finish();
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }


    private void prepareShops() {
        final int[] covers = new int[]{
                R.drawable.amul_shop,
                R.drawable.food_barn,
                R.drawable.juice_shop,
                R.drawable.laundry,
                R.drawable.pal,
                R.drawable.amul_shop,
                R.drawable.food_barn,
                R.drawable.juice_shop,
                R.drawable.laundry,
                R.drawable.pal
        };

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myData = database.getReference("Merchant");
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shopList = new ArrayList<Shop>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shop shop = new Shop(dataSnapshot1.getKey(), (String) dataSnapshot1.child("Name").getValue(), covers[(int) (Math.random() * 8)]);
                    shopList.add(shop);
                }
                ShopAdapter shopAdapter = new ShopAdapter(MainActivity.this, shopList);
                recyclerView1.setAdapter(shopAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


