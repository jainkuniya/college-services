package com.example.himanshu.canteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.himanshu.canteen.client.BillPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by himanshu on 12/12/16.
 */

public class MenuPage extends AppCompatActivity {

    private RecyclerView recyclerView2;
    public static Activity MP;
    private Toolbar mToolbar;
    private Handler mHandler;
    private static final String TAG_HOME = "home";
    private static final String TAG_ORDERS = "previous orders";
    private static final String TAG_LOGOOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private View navHeader;
    private TextView txtName, nameInitial, txtRollno;
    private String clientName, clientRollno, clienInitials;
    public static int navItemIndex = 0;

    private Button billOpen;
    int shopId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        billOpen = (Button) findViewById(R.id.billOpen);
        MP = this;

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_items);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        shopId = getIntent().getIntExtra("shopId", 0);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtRollno = (TextView) navHeader.findViewById(R.id.rollno);
        nameInitial = (TextView) navHeader.findViewById(R.id.name_initial);
        activityTitles = getResources().getStringArray(R.array.user_side_item_titles);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("shopID", shopId);
        editor.commit();

        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 2);
        recyclerView2.setLayoutManager(sLayoutManager);

        prepareItems();

        billOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bill = new Intent(MenuPage.this, BillPage.class);
                startActivity(bill);
            }
        });
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
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        //mToolbar.setTitle(activityTitles[navItemIndex]);
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

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;

                        startActivity(new Intent(MenuPage.this, MainActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_previousOrders:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ORDERS;
                        startActivity(new Intent(MenuPage.this, PreviousOrders.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_LOGOOUT;
                        startActivity(new Intent(MenuPage.this, LoginActivity.class));
                        drawer.closeDrawers();
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

    private void prepareItems() {
        mToolbar.setTitle("Available Items");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String shopId = getIntent().getStringExtra("shopId");
        DatabaseReference myData = database.getReference("Merchant").child(shopId).child("items");
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Items> itemsList = new ArrayList<Items>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try {
                        if (((String) dataSnapshot1.child("isAvailable").getValue()).equals("1")) {
                            Items items = new Items(dataSnapshot1.getKey(),
                                    (String) dataSnapshot1.child("name").getValue(),
                                    Integer.parseInt(String.valueOf(dataSnapshot1.child("price").getValue())),
                                    true);

                            itemsList.add(items);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ItemsAdapter itemsAdapter = new ItemsAdapter(MenuPage.this, itemsList);
                recyclerView2.setAdapter(itemsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
