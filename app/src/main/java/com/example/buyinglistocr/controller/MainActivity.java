package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.TessOCR;

public class MainActivity extends AppCompatActivity {

    private Button mButtonAppareilPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(this.getFilesDir()+"");
        mButtonAppareilPhoto = this.findViewById(R.id.button);
        this.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appareilPhotoActivityIntent = new Intent(MainActivity.this, AppareilPhoto.class);
                startActivity(appareilPhotoActivityIntent);
            }
        });

        /**
        // Allows to open directly the AppareilPhoto activity (comment if not needed)
        Intent appareilPhotoActivityIntent = new Intent(MainActivity.this, AppareilPhoto.class);
        startActivity(appareilPhotoActivityIntent);
        */


    }

}
