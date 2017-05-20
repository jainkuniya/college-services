package com.example.himanshu.canteen;

/**
 * Created by khushboo on 14/1/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.himanshu.canteen.merchant.MerchantPage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.muddzdev.styleabletoastlibrary.StyleableToast;


public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private int checkValue = 2;
    private RadioButton merchant, user;
    private Button logIn;
    private String getId, getPassword;
    public String userName, userInitials, merchantName, merchantInitials;
    private StyleableToast st, st1;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        FirebaseApp.initializeApp(this);

        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        mToolbar.setTitle("Log In");
        setSupportActionBar(mToolbar);
        id = (EditText) findViewById(R.id.rollno);
        password = (EditText) findViewById(R.id.password);
        merchant = (RadioButton) findViewById(R.id.merchants);
        user = (RadioButton) findViewById(R.id.users);
        logIn = (Button) findViewById(R.id.button);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("merchantInfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final SharedPreferences.Editor edit = sharedPref.edit();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (checkValue == 1) {
                    getId = id.getText().toString();
                    getPassword = password.getText().toString();

                    if (getPassword.isEmpty() || getId.isEmpty()) {
                        st1 = new StyleableToast(getApplicationContext(), "Please, Fill Details First", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#865aff"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_info_outline_white_24dp);
                        st1.show();
                    } else {
                        st = new StyleableToast(getApplicationContext(), "Logging In...", Toast.LENGTH_LONG);
                        st.setBackgroundColor(Color.parseColor("#ff5a5f"));
                        st.setTextColor(Color.WHITE);
                        st.setIcon(R.drawable.ic_refresh_white_24dp);
                        st.spinIcon();
                        st.setMaxAlpha();
                        st.show();
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

                                        merchantName = String.valueOf(postSnapshot.child("Name").getValue());
                                        for (int i = 1; i < merchantName.length(); i++) {
                                            if (merchantName.charAt(i) == ' ') {
                                                merchantInitials = ("" + merchantName.charAt(0) + merchantName.charAt(i + 1)).toUpperCase();
                                                break;
                                            }
                                        }
                                        edit.putString("merchantName", merchantName);
                                        edit.putString("merchantID", id);
                                        edit.putString("merchantInitials", merchantInitials);
                                        edit.apply();

                                        startActivity(intent);
                                        finish();
                                        Startup.SU.finish();
                                        break;
                                    }
                                }
                                if (!(id.equals(getId)) || !(password.equals(getPassword))) {
                                    st1 = new StyleableToast(getApplicationContext(), "Sorry, Wrong Credentials", Toast.LENGTH_SHORT);
                                    st1.setBackgroundColor(Color.parseColor("#D32F2F"));
                                    st1.setTextColor(Color.WHITE);
                                    st1.setIcon(R.drawable.ic_error_outline_white_24dp);
                                    st1.show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println(databaseError.getMessage());
                            }
                        });
                    }
                } else if (checkValue == 2) {
                    getId = id.getText().toString();
                    getPassword = password.getText().toString();

                    if (getPassword.isEmpty() || getId.isEmpty()) {
                        st1 = new StyleableToast(getApplicationContext(), "Please, Fill Details First", Toast.LENGTH_SHORT);
                        st1.setBackgroundColor(Color.parseColor("#865aff"));
                        st1.setTextColor(Color.WHITE);
                        st1.setIcon(R.drawable.ic_info_outline_white_24dp);
                        st1.show();
                    } else {
                        st = new StyleableToast(getApplicationContext(), "Logging In...", Toast.LENGTH_LONG);
                        st.setBackgroundColor(Color.parseColor("#ff5a5f"));
                        st.setTextColor(Color.WHITE);
                        st.setIcon(R.drawable.ic_refresh_white_24dp);
                        st.spinIcon();
                        st.setMaxAlpha();
                        st.show();
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

                                        userName = String.valueOf(postSnapshot.child("Name").getValue());
                                        for (int i = 1; i < userName.length(); i++) {
                                            if (userName.charAt(i) == ' ') {
                                                userInitials = ("" + userName.charAt(0) + userName.charAt(i + 1)).toUpperCase();
                                                break;
                                            }
                                        }
                                        editor.putString("userName", userName);
                                        editor.putString("userRollNo", id);
                                        editor.putString("userInitials", userInitials);
                                        editor.apply();
                                        startActivity(intent);
                                        finish();
                                        Startup.SU.finish();
                                        break;
                                    }
                                }
                                if (!(id.equals(getId)) || !(password.equals(getPassword))) {
                                    st1 = new StyleableToast(getApplicationContext(), "Sorry, Wrong Credentials", Toast.LENGTH_SHORT);
                                    st1.setBackgroundColor(Color.parseColor("#D32F2F"));
                                    st1.setTextColor(Color.WHITE);
                                    st1.setIcon(R.drawable.ic_error_outline_white_24dp);
                                    st1.show();
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
    }

    public void signup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
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

