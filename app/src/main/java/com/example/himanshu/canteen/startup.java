package com.example.himanshu.canteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class startup extends AppCompatActivity {
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        cardView = (CardView) findViewById(R.id.card_view);
    }
    public void login(View view){
        Intent intent = new Intent(this, login_activity.class);

        startActivity(intent);
    }
    public void signup(View view){
        Intent intent = new Intent(this, signUp.class);

        startActivity(intent);
    }
}
