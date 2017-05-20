package com.example.himanshu.canteen.merchant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.himanshu.canteen.LoginActivity;
import com.example.himanshu.canteen.OrderToMerchant;
import com.example.himanshu.canteen.R;
import com.example.himanshu.canteen.merchant.adapter.OrderMerchantAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MerchantPage extends AppCompatActivity {
    private ArrayList<OrderToMerchant> orderList;
    private RecyclerView recyclerView;
    private CardView cardview;

    private Toolbar mToolbar;
    private static final String TAG_HOME = "home";
    private static final String TAG_ITEMS = "change items";
    private static final String TAG_LOGOOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private View navHeader;
    private TextView txtName, nameInitial, txtRollno;
    private String merchantName, merchantID, merchantInitials;
    public static int navItemIndex = 0;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_page);

        cardview = (CardView) findViewById(R.id.card_view_merorder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_merchant);
        orderList = new ArrayList<>();

        sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);

        RecyclerView.LayoutManager oLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(oLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.other_nav_view);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtRollno = (TextView) navHeader.findViewById(R.id.rollno);
        nameInitial = (TextView) navHeader.findViewById(R.id.name_initial);
        activityTitles = getResources().getStringArray(R.array.merchant_side_item_titles);

        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        prepareOrder();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void loadNavHeader() {

        SharedPreferences sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);
        merchantName = sharedPref.getString("merchantName", "");
        merchantID = sharedPref.getString("merchantID", "");
        merchantInitials = sharedPref.getString("merchantInitials", "");
        txtName.setText("" + merchantName);
        txtRollno.setText("" + merchantID);
        nameInitial.setText("" + merchantInitials);
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
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_changeItems:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ITEMS;
                        startActivity(new Intent(MerchantPage.this, ChangeItems.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 2;
                        SharedPreferences sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("merchantID", "");
                        editor.apply();
                        CURRENT_TAG = TAG_LOGOOUT;
                        startActivity(new Intent(MerchantPage.this, LoginActivity.class));
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

    private void prepareOrder() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myData = database.getReference("Merchant").child(sharedPref.getString("merchantID", "")).child("OrderID");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList = new ArrayList<OrderToMerchant>();
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
                                        String.valueOf(dataSnapshot.child("StudID").getValue()),
                                        items,
                                        String.valueOf(dataSnapshot.child("Total Price").getValue())
                                        , String.valueOf(dataSnapshot.child("IsOrderConfirmed").getValue()).equals("1"),
                                        false);

                                orderList.add(orderToMerchant);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                final OrderMerchantAdapter orderMerchantAdapter = new OrderMerchantAdapter(orderList) {
                    @Override
                    public void updateOrder(String id, String isVerified, String isDelivered) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myData = database.getReference("Orders");
                        myData.child(id).child("IsOrderConfirmed").setValue(isVerified);
                        myData.child(id).child("IsOrderDelievered").setValue(isDelivered);
                    }
                };
                recyclerView.setAdapter(orderMerchantAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
