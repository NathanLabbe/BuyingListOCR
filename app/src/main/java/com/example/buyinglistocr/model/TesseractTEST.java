package com.example.buyinglistocr.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.buyinglistocr.R;

public class TesseractTEST extends AppCompatActivity {
    TessOCR mTessOCR = new TessOCR(this, "fra");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesseract_test);
    }
}
