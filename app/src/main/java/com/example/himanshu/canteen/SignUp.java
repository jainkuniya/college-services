package com.example.himanshu.canteen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.attr.data;

public class SignUp extends AppCompatActivity {

    private EditText name, idNo, password, mobileNo;
    private Button signUp;
    private RadioButton merchant, user;
    private int checkValue=2;
    private String getName, getPassword, getIdNo, getMobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);

        name = (EditText) findViewById(R.id.name);
        idNo = (EditText) findViewById(R.id.rollno);
        password = (EditText) findViewById(R.id.password);
        mobileNo = (EditText) findViewById(R.id.mobile);
        signUp = (Button) findViewById(R.id.buttonsign);

        merchant = (RadioButton) findViewById(R.id.merchant);
        user = (RadioButton) findViewById(R.id.user);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (checkValue == 1) {
                    getName = name.getText().toString();
                    getIdNo = idNo.getText().toString();
                    getMobileNo = mobileNo.getText().toString();
                    getPassword = password.getText().toString();

                    if (getName.isEmpty() || getPassword.isEmpty() || getMobileNo.isEmpty() || getIdNo.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Fill Details First", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference myData = database.getReference("Merchant");
                        myData.child(getIdNo).child("Name").setValue(getName);
                        myData.child(getIdNo).child("Mobile No").setValue(getMobileNo);
                        myData.child(getIdNo).child("Password").setValue(getPassword);
                        Toast.makeText(getApplicationContext(), "Successfully Done", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(SignUp.this, Startup.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (checkValue == 2) {

                    getName = name.getText().toString();
                    getIdNo = idNo.getText().toString();
                    getMobileNo = mobileNo.getText().toString();
                    getPassword = password.getText().toString();

                    if (getName.isEmpty() || getPassword.isEmpty() || getMobileNo.isEmpty() || getIdNo.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Fill Details First", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference myData = database.getReference("Client");
                        myData.child(getIdNo).child("Name").setValue(getName);
                        myData.child(getIdNo).child("Mobile No").setValue(getMobileNo);
                        myData.child(getIdNo).child("Password").setValue(getPassword);
                        Toast.makeText(getApplicationContext(), "Successfully Done", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(SignUp.this, Startup.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

    }

    public void checkedRadio(View view) {
        onRadioButtonClicked();
    }

    public void onRadioButtonClicked() {


        merchant = (RadioButton) findViewById(R.id.merchant);
        user = (RadioButton) findViewById(R.id.user);

        if (merchant.isChecked()) {
            checkValue = 1;

        }
        if (user.isChecked()) {
            checkValue = 2;
        }


    }
}
