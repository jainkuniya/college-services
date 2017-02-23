package com.example.himanshu.canteen;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class SignUp extends AppCompatActivity {

    private EditText name, idNo, password, mobileNo;
    private Button signUp;
    private RadioButton merchant, user;
    private int checkValue = 2;
    private String getName, getPassword, getIdNo, getMobileNo;
    private StyleableToast st1;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);

        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setTitle("Sign Up");
        setSupportActionBar(mToolbar);

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
                        st1 = new StyleableToast(getApplicationContext(), "Please, Fill Details First", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#865aff"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_info_outline_white_24dp);
                        st1.show();
                    } else {
                        DatabaseReference myData = database.getReference("Merchant");
                        myData.child(getIdNo).child("Name").setValue(getName);
                        myData.child(getIdNo).child("Mobile No").setValue(getMobileNo);
                        myData.child(getIdNo).child("Password").setValue(getPassword);
                        st1 = new StyleableToast(getApplicationContext(), "Successfully Done", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#689F38"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_done_white_24dp);
                        st1.show();
                        Intent intent1 = new Intent(SignUp.this, Startup.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                if (checkValue == 2) {

                    getName = name.getText().toString();
                    getIdNo = idNo.getText().toString();
                    getMobileNo = mobileNo.getText().toString();
                    getPassword = password.getText().toString();

                    if (getName.isEmpty() || getPassword.isEmpty() || getMobileNo.isEmpty() || getIdNo.isEmpty()) {
                        st1 = new StyleableToast(getApplicationContext(), "Please, Fill Details First", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#865aff"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_info_outline_white_24dp);
                        st1.show();
                    } else {
                        DatabaseReference myData = database.getReference("Client");
                        myData.child(getIdNo).child("Name").setValue(getName);
                        myData.child(getIdNo).child("Mobile No").setValue(getMobileNo);
                        myData.child(getIdNo).child("Password").setValue(getPassword);
                        st1 = new StyleableToast(getApplicationContext(), "Successfully Done", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#689F38"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_done_white_24dp);
                        st1.show();
                        Intent intent2 = new Intent(SignUp.this, Startup.class);
                        startActivity(intent2);
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
