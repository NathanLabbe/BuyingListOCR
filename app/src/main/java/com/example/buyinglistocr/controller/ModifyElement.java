package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.Item;
import com.example.buyinglistocr.model.ItemDAO;
import com.example.buyinglistocr.model.ProductDAO;

import java.util.ArrayList;

public class ModifyElement extends AppCompatActivity {

    // access to the database
    ItemDAO itemDAO;

    // reference
    EditText nameInput;
    Button modifyElementBtn;
    Button deleteElementBtn;
    TextView alertTextView;

    // attribute
    long idItem;
    long idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_element);

        // access to the database
        itemDAO = new ItemDAO(ModifyElement.this);

        Toolbar toolbar = findViewById(R.id.toolbarModify);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new         Intent(getApplicationContext(),MainActivity.class));
                Intent ListViewIntent = new Intent(ModifyElement.this, ListView.class);
                ListViewIntent.putExtra("idList", idList);
                startActivity(ListViewIntent);

            }
        });

        // displays references on the activity view
        nameInput = (EditText) findViewById(R.id.activity_modify_element_name_input);
        modifyElementBtn = (Button) findViewById(R.id.activity_modify_element_modify_btn);
        deleteElementBtn = (Button) findViewById(R.id.activity_modify_element_delete_btn);
        alertTextView = (TextView) findViewById(R.id.activity_modify_element_alertTextView);

        // TODO
        alertTextView.setVisibility(View.INVISIBLE);

        // get the idList from our current list
        Intent intent = getIntent();
        idItem = intent.getLongExtra("idItem", 0);
        idList = intent.getLongExtra("idList", 0);

        // TODO
        //nameInput.setHint(productDAO.getName(idProduct).toString());
        //System.out.println("TEST : " + productDAO.getName(idProduct).toString());

        modifyElementBtn.setEnabled(false);

        nameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modifyElementBtn.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    /**
     * Update the product in the current list
     * @param view
     */
    public void modifyElt(View view) {

        String str = nameInput.getText().toString();

        if (isPresent(str, idList)) {
            alertTextView.setText("This product name already exist in your list !");
            alertTextView.setVisibility(View.VISIBLE);
        } else {
            Item item = itemDAO.getItem(idItem);
            item.setName(str);
            itemDAO.update(item);
            Intent ListViewIntent = new Intent(ModifyElement.this, ListView.class);
            ListViewIntent.putExtra("idList", idList);
            startActivity(ListViewIntent);
        }

    }

    /**
     * Delete the product in the current list
     * @param view
     */
    public void deleteElt(View view) {

        itemDAO.delete(idItem);
        Intent ListViewIntent = new Intent(ModifyElement.this, ListView.class);
        ListViewIntent.putExtra("idList", idList);
        startActivity(ListViewIntent);

    }

    /**
     *
     * @param str
     * @param idList
     * @return
     */
    public boolean isPresent(String str, long idList) {

        // The return value
        Boolean ret = false;

        // Get all items of our list
        ArrayList<Item> items = itemDAO.get(idList);

        for (Item item : items) {
            if (item.getName().equals(str)) {
                ret = true;
            }
        }

        return ret;

    }
}