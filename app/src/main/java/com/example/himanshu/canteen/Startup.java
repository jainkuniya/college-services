package com.example.himanshu.canteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.himanshu.canteen.merchant.MerchantPage;

public class Startup extends AppCompatActivity {
    private CardView cardView;
    public static Activity SU;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        SU = this;
        cardView = (CardView) findViewById(R.id.card_view);

        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setTitle("College Services");
        setSupportActionBar(mToolbar);

        //check for login
        checkForLogin();
    }

    private void checkForLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPref.getString("merchantID", ""))) {
            startActivity(new Intent(this, MerchantPage.class));
            finish();
        } else if (!TextUtils.isEmpty(sharedPreferences.getString("userRollNo", ""))) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
