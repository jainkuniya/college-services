package com.example.himanshu.canteen;

/**
 * Created by khushboo on 14/1/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private int g;
    private RadioButton merchant, user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        id = (EditText) findViewById(R.id.rollno);
        password=(EditText) findViewById(R.id.password);

        merchant = (RadioButton) findViewById(R.id.merchants);
        user = (RadioButton) findViewById(R.id.users);

        if (merchant.isChecked()) {
            g=1;

        }
        if(user.isChecked()){
            g=2;
        }




    }
    public void signup(View view){
        Intent intent = new Intent(this, SignUp.class);

        startActivity(intent);
    }
    public void clickedlogin(View view){
        Editable idEditable = id.getText();
        String id = idEditable.toString();


        Editable passEditable = password.getText();
        String password = passEditable.toString();

        if(g==1){

            if(id.equals("1")&&password.equals("1")){

                Intent merchantPage=new Intent(this,merchantPage.class);
                startActivity(merchantPage);

            }
            else /*if((id!="2" && password!="2")||(password!="1"&& id!="1"))*/{
                Toast.makeText(getApplicationContext(),"incorrect id password!!",
                        Toast.LENGTH_SHORT).show();

            }

        }
        else if(g==2){
            if(id.equals("2")&&password.equals("2")) {

                Intent mainPage = new Intent(this, MainActivity.class);
                startActivity(mainPage);
            }
            else /*if((id!="2" && password!="2")||(password!="1"&& id!="1"))*/{
                Toast.makeText(getApplicationContext(),"incorrect id password!!",
                        Toast.LENGTH_SHORT).show();

            }
        }
        /*else if((id!="2" && password!="2")||(password!="1"&& id!="1")){
            Toast.makeText(getApplicationContext(),"incorrect id password!!",
                    Toast.LENGTH_SHORT).show();

        }*/

    }
    public void checkedRadio(View view){
        onRadioButtonClicked();
    }

    public void onRadioButtonClicked(){




        merchant = (RadioButton) findViewById(R.id.merchants);
        user = (RadioButton) findViewById(R.id.users);

        if (merchant.isChecked()) {
            g=1;

        }
        if(user.isChecked()){
           g=2;
        }


    }
}

