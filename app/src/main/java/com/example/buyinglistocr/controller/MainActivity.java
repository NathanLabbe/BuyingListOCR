package com.example.buyinglistocr.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.buyinglistocr.R;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingText.setText("Bonjour");
    }
    //test
}
