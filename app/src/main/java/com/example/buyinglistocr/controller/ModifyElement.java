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

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.ProductDAO;

public class ModifyElement extends AppCompatActivity {

    // access to the database
    ProductDAO productDAO;

    // reference
    EditText nameInput;
    Button modifyElementBtn;
    Button deleteElementBtn;

    // attribute
    long idProduct;
    long idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_element);

        // access to the database
        productDAO = new ProductDAO(ModifyElement.this);

        // display actionbar
        /*
        getSupportActionBar().setTitle("ModifyElement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
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

        // get the idList from our current list
        Intent intent = getIntent();
        idProduct = intent.getLongExtra("idProduct", 0);
        idList = intent.getLongExtra("idList", 0);

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

        productDAO.updateName(idProduct, nameInput.getText().toString());
        Intent ListViewIntent = new Intent(ModifyElement.this, ListView.class);
        ListViewIntent.putExtra("idList", idList);
        startActivity(ListViewIntent);

    }

    /**
     * Delete the product in the current list
     * @param view
     */
    public void deleteElt(View view) {

        productDAO.delete(idProduct);
        Intent ListViewIntent = new Intent(ModifyElement.this, ListView.class);
        ListViewIntent.putExtra("idList", idList);
        startActivity(ListViewIntent);

    }
}