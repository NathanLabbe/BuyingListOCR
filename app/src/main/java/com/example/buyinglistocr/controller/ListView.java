package com.example.buyinglistocr.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.buyinglistocr.R;

import java.util.ArrayList;
import java.util.Iterator;

public class ListView extends AppCompatActivity {

    //ACCES A LA BASE DE DONNEE
    private ProductDAO productDAO;

    //REFERENCE
    Button addNewItem;
    android.widget.ListView listView;
    Button test;

    //LISTE DE STRING QUI SERVIRA COMME LISTE D'ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        productDAO = new ProductDAO(ListView.this);

        //AFFICHAGE DES REFERENCES
        addNewItem = (Button) findViewById(R.id.activity_main_activity_add_new_item);
        listView = (android.widget.ListView) findViewById(R.id.activity_main_list_view);
        test = (Button) findViewById(R.id.button2);


        //AFFICHAGE DES ELEMENTS DE NOTRE LISTE
        viewData();

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //INFORMATION SUR L'ITEM
                String str = listView.getItemAtPosition(position).toString();
                System.out.println("NAME : " + str);
                System.out.println("ID : " + accesBD.getId(str));

                long idElt = accesBD.getId(str);

                Intent ModifyElementIntent = new Intent(MainActivity.this, ModifyElement.class);
                ModifyElementIntent.putExtra("idElt", idElt);
                startActivity(ModifyElementIntent);
            }
        });
        */
    }

    private void viewData() {

            ArrayList<String> names = productDAO.getNames();
            Iterator<String> it = names.iterator();

            while(it.hasNext()){
                listItems.add(it.next());
            }

            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems);
            listView.setAdapter(adapter);

    }


    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItemsView(View v) {
        Intent AddElementIntent = new Intent(ListView.this, AddElement.class);
        startActivity(AddElementIntent);
    }
}
