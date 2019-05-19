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
import com.example.buyinglistocr.model.Product;
import com.example.buyinglistocr.model.ProductDAO;

public class AddElement extends AppCompatActivity {

    // access to the database
    ProductDAO productDAO;
    private long idList;

    // reference
    EditText nameInput;
    Button addElementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element);

        // access to the database
        productDAO = new ProductDAO(this);

        // display the actionbar
        /*
        getSupportActionBar().setTitle("AddElement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
        Toolbar toolbar = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new         Intent(getApplicationContext(),MainActivity.class));
                Intent ListViewIntent = new Intent(AddElement.this, ListView.class);
                ListViewIntent.putExtra("idList", idList);
                startActivity(ListViewIntent);
            }
        });
        
        // displays references on the activity view
        nameInput = (EditText) findViewById(R.id.activity_add_element_name_input);
        addElementBtn = (Button) findViewById(R.id.activity_add_element_add_button);

        // get the idList from our current list
        Intent intent = getIntent();
        idList = intent.getLongExtra("idList", 0);
        // TEST
        System.out.println("AddElement : " + idList);

        addElementBtn.setEnabled(false);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addElementBtn.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * Add a product to our list
     * @param v
     */
    public void addElement(View v) {
        String str = nameInput.getText().toString();
        Product product = new Product(str, 0, 0, "", 0, idList);
        productDAO.add(product);

        Intent ListViewIntent = new Intent(AddElement.this, ListView.class);
        ListViewIntent.putExtra("idList",idList);
        startActivity(ListViewIntent);
    }


}
