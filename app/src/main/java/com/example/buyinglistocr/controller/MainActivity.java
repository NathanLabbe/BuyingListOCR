package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.TessOCR;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(this.getFilesDir()+"");
        /*
        // Allows to open directly the AppareilPhoto activity (comment if not needed)
        Intent appareilPhotoActivityIntent = new Intent(MainActivity.this, AppareilPhoto.class);
        startActivity(appareilPhotoActivityIntent);
        */

        TessOCR mTessOCR = new TessOCR(this , "fra");

    }

}
