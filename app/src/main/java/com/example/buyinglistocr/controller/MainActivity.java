package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.buyinglistocr.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
        // Allows to open directly the AppareilPhoto activity (comment if not needed)
        Intent appareilPhotoActivityIntent = new Intent(MainActivity.this, AppareilPhoto.class);
        startActivity(appareilPhotoActivityIntent);
        */
    }
}
