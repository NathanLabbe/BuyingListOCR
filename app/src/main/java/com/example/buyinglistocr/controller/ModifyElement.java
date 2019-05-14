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

public class ModifyElement extends AppCompatActivity {
    /**
    //ACCES DE LA BASE DE DONNEE
    AccesLocal accesBD;

    //REFERENCE
    EditText nameInput;
    Button modifyElementBtn;
    Button deleteElementBtn;

    long idElt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_element);

        accesBD = new AccesLocal(this.getBaseContext());

        //AFFICHE LE BOUTON SUR L'ACTIONBAR
        getSupportActionBar().setTitle("ModifyElement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //AFFICHAGE DES REFERENCES
        nameInput = (EditText) findViewById(R.id.activity_modify_element_name_input);
        modifyElementBtn = (Button) findViewById(R.id.activity_modify_element_modify_btn);
        deleteElementBtn = (Button) findViewById(R.id.activity_modify_element_delete_btn);

        Intent intent = getIntent();
        idElt = intent.getLongExtra("idElt", 0);

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

    public void modifyElt(View view) {
        accesBD.updateId(idElt, nameInput.getText().toString());

        Intent MainActivityIntent = new Intent(ModifyElement.this, MainActivity.class);
        startActivity(MainActivityIntent);
    }

    public void deleteElt(View view) {
        if (accesBD.deleteId(idElt)) {
            System.out.println("sucess");
        } else {
            System.out.println("fail");
        }

        Intent MainActivityIntent = new Intent(ModifyElement.this, MainActivity.class);
        startActivity(MainActivityIntent);
    }
    */
}
