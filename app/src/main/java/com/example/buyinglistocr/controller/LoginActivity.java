package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.User;
import com.example.buyinglistocr.model.UserManager;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;

    private EditText editTextLogin;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = new UserManager(this);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);



        final Button buttonLogin = findViewById(R.id.buttonLogin);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        if(preferences.getInt("check", 0)==0){
            checkBoxRemember.setChecked(false);
        }else{
            checkBoxRemember.setChecked(true);
        }

        editTextLogin.setText(preferences.getString("username", ""));
        editTextPassword.setText(preferences.getString("password", ""));


        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if(editTextLogin.length() == 0 || editTextPassword.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Please, fill all the fields ", Toast.LENGTH_LONG).show();

                } else {
                    preferences.edit().putString("username",editTextLogin.getText().toString()).commit();
                    if(checkBoxRemember.isChecked()){
                        preferences.edit().putInt("check",1).commit();
                        preferences.edit().putString("password",editTextPassword.getText().toString()).commit();
                    }else{
                        preferences.edit().putInt("check",0).commit();
                        preferences.edit().putString("password","").commit();
                    }
                    userManager.login(new User(0, editTextLogin.getText().toString(), editTextPassword.getText().toString(), null));

                }

            }

        });

        TextView textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

                finish();

            }

        });

    }

}
