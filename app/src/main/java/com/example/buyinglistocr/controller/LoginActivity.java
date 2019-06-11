package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.User;
import com.example.buyinglistocr.model.UserManager;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;

    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = new UserManager(this);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(editTextLogin.length() == 0 || editTextPassword.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Please, fill all the fields ", Toast.LENGTH_LONG).show();

                } else {

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
