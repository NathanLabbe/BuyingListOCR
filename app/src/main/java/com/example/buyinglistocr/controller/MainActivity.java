package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.buyinglistocr.model.List;

import com.example.buyinglistocr.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListDAO listDAO;

    ArrayList<String> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listDAO = new ListDAO(this);

        test = listDAO.get();

        final RecyclerView rv = findViewById(R.id.list);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new MyAdapter(test));

        Button button = findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                List list = new List(0, "Luffy", 0);

                listDAO.add(list);

                test.add(list.getName());

                rv.getAdapter().notifyItemInserted(test.size() - 1);


            }

        });

        Button button2 = findViewById(R.id.test2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AppareilPhoto.class);
                startActivity(intent);


            }

        });

    }

}