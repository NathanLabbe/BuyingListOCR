package com.example.buyinglistocr.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import com.example.buyinglistocr.model.List;

import com.example.buyinglistocr.R;

/**
 * Allow to represent the main activity
 */
public class MainActivity extends AppCompatActivity {

    // The recycler view
    private RecyclerView rv;

    // The list DAO
    private ListDAO listDAO;

    // The ArrayList of list
    private ArrayList<Pair<Long, String>> lists;

    /**
     * Method that be executed during the creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the list DAO
        listDAO = new ListDAO(this);

        // Get the data
        lists = listDAO.get();

        // Define the recycler view
        rv = findViewById(R.id.list);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new MyAdapter(MainActivity.this, lists));

        // Define the buttonAdd
        FloatingActionButton buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Launch the alert dialog
                showAlertDialogButtonClicked(view);

            }

        });

        // Define the buttonCamera
        FloatingActionButton buttonCamera = findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Start the new activity
                Intent intent = new Intent(MainActivity.this, AppareilPhoto.class);
                startActivity(intent);


            }

        });

    }

    /**
     * Method that be executed during the resume of the activity
     */
    @Override
    public void onResume(){

        super.onResume();

        // Notify the data set changed
        rv.getAdapter().notifyDataSetChanged();

    }

    /**
     * Allow to define the alert dialog
     * @param view - The view
     */
    public void showAlertDialogButtonClicked(View view) {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_list, null);
        builder.setView(customLayout);

        // Define the positive button
        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = customLayout.findViewById(R.id.name);

                // Create the new list with the data of the edit text
                List list = new List(0, editText.getText().toString(), 0);

                // Add this list to the database and get it id
                long idList = listDAO.add(list);

                // Add this list to the ArrayList
                lists.add(Pair.create(idList, list.getName()));

                // Notify the recycler view that a data is inserted
                rv.getAdapter().notifyItemInserted(lists.size() - 1);

            }

        });

        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}