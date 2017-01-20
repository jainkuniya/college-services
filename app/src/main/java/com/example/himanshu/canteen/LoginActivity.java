package com.example.himanshu.canteen;

/**
 * Created by khushboo on 14/1/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;




public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

    }
    public void signup(View view){
        Intent intent = new Intent(this, SignUp.class);

        startActivity(intent);
    }
    public void mainPage(View view){

        Intent mainPage=new Intent(this,MainActivity.class);
        startActivity(mainPage);
    }

    public void  onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.merchants:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.users:
                if (checked)
                    // Ninjas rule
                    break;
        }

    }
}

