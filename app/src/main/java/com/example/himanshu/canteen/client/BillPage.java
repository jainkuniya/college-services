package com.example.himanshu.canteen.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.himanshu.canteen.Items;
import com.example.himanshu.canteen.LoginActivity;
import com.example.himanshu.canteen.MainActivity;
import com.example.himanshu.canteen.PreviousOrders;
import com.example.himanshu.canteen.R;
import com.example.himanshu.canteen.Singleton;
import com.example.himanshu.canteen.YourOrdersAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by himanshu on 2/2/17.
 */

public class BillPage extends AppCompatActivity {
    private RecyclerView orders;
    private YourOrdersAdapter yourOrdersAdapter;

    private Toolbar toolbar;
    private Handler mHandler;
    private static final String TAG_HOME = "home";
    private static final String TAG_ORDERS = "previous orders";
    private static final String TAG_LOGOOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;
    private String[] activityTitles, orderItemName;
    private int[] orderTotal;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private View navHeader;
    private TextView txtName, nameInitial, txtRollno;
    private String clientName, clientRollno, clienInitials, orderID;
    private int navItemIndex, totalPrice = 0, shopID = 0;
    public static long count = 0, countOrder1 = 0, random;
    private HashMap<Long, Items> sparseArray;
    private Items order;
    private Map<String, Integer> users = new HashMap<String, Integer>();
    private ArrayList<Items> array=new ArrayList<Items>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_page);

        FirebaseApp.initializeApp(this);
        sparseArray = Singleton.getInstance().getItemsSparseArray();
        array.addAll(sparseArray.values());

        orders = (RecyclerView) findViewById(R.id.orders);
        yourOrdersAdapter = new YourOrdersAdapter(array);


        RecyclerView.LayoutManager orderLayoutManager = new LinearLayoutManager(getApplicationContext());
        orders.setLayoutManager(orderLayoutManager);
        orders.setAdapter(yourOrdersAdapter);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtRollno = (TextView) navHeader.findViewById(R.id.rollno);
        nameInitial = (TextView) navHeader.findViewById(R.id.name_initial);
        activityTitles = getResources().getStringArray(R.array.user_side_item_titles);

        loadNavHeader();
        setUpNavigationView();

        orderItemName = new String[sparseArray.size()];
        orderTotal = new int[sparseArray.size()];

        Iterator it = sparseArray.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
           // System.out.println(pair.getKey() + " = " + pair.getValue());
           order= sparseArray.get(pair.getKey());
            users.put(order.getItemName(), order.getItemPrice() * order.getItemQty());
            totalPrice = totalPrice + (order.getItemPrice() * order.getItemQty());
            it.remove(); // avoids a ConcurrentModificationException
        }
        /*for (int i = 0; i < sparseArray.size(); i++) {
            int key = sparseArray.keyAt(i);
            order = sparseArray.get(key);
            users.put(order.getItemName(), order.getItemPrice() * order.getItemQty());
            totalPrice = totalPrice + (order.getItemPrice() * order.getItemQty());
        }
        */
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                shopID = sharedPref.getInt("shopID", 0);
                clientRollno = sharedPref.getString("userRollNo", "");

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference orderChild = database.getReference("Orders");

                random = System.currentTimeMillis();
                orderChild.child(String.valueOf(random)).child("ShopID").setValue(shopID);
                orderChild.child(String.valueOf(random)).child("StudID").setValue(clientRollno);
                orderChild.child(String.valueOf(random)).child("IsOrderConfirmed").setValue(0);
                orderChild.child(String.valueOf(random)).child("IsOrderDelievered").setValue(0);
                orderChild.child(String.valueOf(random)).child("Items").setValue(users);
                orderChild.child(String.valueOf(random)).child("Total Price").setValue(totalPrice);
                orderChild.getParent().child("Client/" + clientRollno + "/Orders").child("OrderID" + random).setValue(random);
                orderChild.getParent().child("Merchant/" + (100 + shopID) + "/OrderID").child("OrderID" + random).setValue(random);

                sparseArray.clear();
                startActivity(new Intent(BillPage.this,MainActivity.class ));   }
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

        //invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Order Bill");
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(0).setChecked(true);
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
                        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
                        CURRENT_TAG = TAG_HOME;
                        startActivity(new Intent(BillPage.this, MainActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_previousOrders:
                        navItemIndex = 1;
                        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
                        CURRENT_TAG = TAG_ORDERS;
                        startActivity(new Intent(BillPage.this, PreviousOrders.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 2;
                        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
                        CURRENT_TAG = TAG_LOGOOUT;
                        startActivity(new Intent(BillPage.this, LoginActivity.class));
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
}
