package com.example.himanshu.canteen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class Startup extends AppCompatActivity {
    private CardView cardView;
    public static Activity SU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        SU = this;
        cardView = (CardView) findViewById(R.id.card_view);
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
