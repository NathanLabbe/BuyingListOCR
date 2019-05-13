package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.buyinglistocr.R;

public class AddElement extends AppCompatActivity {

    //ACCES A LA BASE DE DONNEE
    ProductDAO accesBD;

    //REFERENCE
    EditText nameInput;
    Button addElementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element);

        accesBD = new AccesLocal(this.getBaseContext());

        //AFFICHE LE BOUTON SUR L'ACTIONBAR
        getSupportActionBar().setTitle("AddElement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameInput = (EditText) findViewById(R.id.activity_add_element_name_input);
        addElementBtn = (Button) findViewById(R.id.activity_add_element_add_button);

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

    public void addItems(View v) {
        String str = nameInput.getText().toString();
        Element elt = new Element(str);
        accesBD.add(elt);

        Intent MainActivityIntent = new Intent(AddElement.this, MainActivity.class);
        startActivity(MainActivityIntent);
    }
}
