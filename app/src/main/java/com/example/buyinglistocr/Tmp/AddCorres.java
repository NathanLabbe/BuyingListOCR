package com.example.buyinglistocr.Tmp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.buyinglistocr.R;

import java.util.ArrayList;

public class AddCorres extends AppCompatActivity {

    private SavePurchaseDAO savePurchaseDAO;

    // The recycler view
    private RecyclerView rv;

    // The ArrayList of Item
    private ArrayList<SavePurchase> savedPurchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_corres);

        // Get the data
        //purchases = SavePurchaseDAO.get(list.getId());

        // Define the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help us!");

        // Define the recycler view
        rv = findViewById(R.id.purchase);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //rv.setAdapter(new AdapterItems(this, savedPurcheses, rv));
    }
}
