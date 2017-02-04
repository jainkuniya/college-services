package com.example.himanshu.canteen;

/**
 * Created by khushboo on 14/1/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;


public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private int checkValue=2;
    private RadioButton merchant, user;
    private Button logIn;
    private String getId, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        FirebaseApp.initializeApp(this);

        id = (EditText) findViewById(R.id.rollno);
        password = (EditText) findViewById(R.id.password);
        merchant = (RadioButton) findViewById(R.id.merchants);
        user = (RadioButton) findViewById(R.id.users);
        logIn = (Button) findViewById(R.id.button);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (checkValue == 1) {
                    getId = id.getText().toString();
                    getPassword = password.getText().toString();

                    if (getPassword.isEmpty() || getId.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Fill Details First", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DatabaseReference myData = database.getReference("Merchant");
                        myData.addValueEventListener(new ValueEventListener() {
                            String id, password;
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    id = String.valueOf(postSnapshot.getKey());
                                    password = String.valueOf(postSnapshot.child("Password").getValue());

                                    if (id.equals(getId) && password.equals(getPassword)) {
                                        Intent intent = new Intent(LoginActivity.this, MerchantPage.class);
                                        startActivity(intent);
                                        finish();
                                        Startup.SU.finish();
                                        break;
                                    }
                                }
                                if(!(id.equals(getId)) || !(password.equals(getPassword))) {
                                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println(databaseError.getMessage());
                            }
                        });
                    }
                }
                else if (checkValue == 2) {
                    getId = id.getText().toString();
                    getPassword = password.getText().toString();

                    if (getPassword.isEmpty() || getId.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Fill Details First", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DatabaseReference myData = database.getReference("Client");
                        myData.addValueEventListener(new ValueEventListener() {
                            String id, password;
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    id = String.valueOf(postSnapshot.getKey());
                                    password = String.valueOf(postSnapshot.child("Password").getValue());

                                    if (id.equals(getId) && password.equals(getPassword)) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Startup.SU.finish();
                                        break;
                                    }
                                }
                                if(!(id.equals(getId)) || !(password.equals(getPassword))) {
                                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println(databaseError.getMessage());
                            }
                        });
                    }
                }
            }
        });



        /*if (merchant.isChecked()) {
            checkValue=1;

        }
        if(user.isChecked()){
            checkValue=2;
        }*/


    }

    public void signup(View view) {
        Intent intent = new Intent(this, SignUp.class);

        startActivity(intent);
    }

    public void clickedlogin(View view) {
        Editable idEditable = id.getText();
        String id = idEditable.toString();


        Editable passEditable = password.getText();
        String password = passEditable.toString();

        if (checkValue == 1) {

            if (id.equals("1") && password.equals("1")) {

                Intent merchantPage = new Intent(this, MerchantPage.class);
                startActivity(merchantPage);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "incorrect id password!!",
                        Toast.LENGTH_SHORT).show();

            }

        } else if (checkValue == 2) {
            if (id.equals("2") && password.equals("2")) {

                Intent mainPage = new Intent(this, MainActivity.class);
                startActivity(mainPage);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect Credentials!!",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void checkedRadio(View view) {
        onRadioButtonClicked();
    }

    public void onRadioButtonClicked() {


        merchant = (RadioButton) findViewById(R.id.merchants);
        user = (RadioButton) findViewById(R.id.users);

        if (merchant.isChecked()) {
            checkValue = 1;

        }
        if (user.isChecked()) {
            checkValue = 2;
        }


    }
}

