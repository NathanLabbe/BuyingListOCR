package com.example.buyinglistocr.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.model.ListManager;
import com.example.buyinglistocr.util.SharedPreferencesUser;
import com.example.buyinglistocr.util.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ListsActivity extends AppCompatActivity {

    private ListManager listManager;

    private ArrayList<List> lists;

    private List list;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        listManager = new ListManager(this);

        Toolbar toolbar = findViewById(R.id.toolbarListsActivity);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + SharedPreferencesUser.getInstance(this).getLogin() + " - My Lists");

        lists = new ArrayList<>();

        listManager.getAll(new VolleyCallback() {

            @Override
            public void onSuccess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("error")) {

                        Toast.makeText(ListsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("lists");

                        for (int i = 0 ; i < jsonArray.length() ; i++) {

                            JSONObject jsonObjectList = jsonArray.getJSONObject(i);

                            lists.add(new List(jsonObjectList.getInt("id"), jsonObjectList.getString("name"), jsonObjectList.getDouble("spent"), jsonObjectList.getInt("status"), jsonObjectList.getInt("idUser")));

                        }

                    }

                } catch(JSONException e) {

                    e.printStackTrace();

                }

                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

            }

        });

        recyclerView = findViewById(R.id.recyclerViewLists);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new AdapterLists(ListsActivity.this, lists));

        FloatingActionButton buttonAdd = findViewById(R.id.floatingButtonAddList);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showAlertDialogButtonClicked(view);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.participate) {

            Intent ParticipateIntent = new Intent(ListsActivity.this, AddCorres.class);
            startActivity(ParticipateIntent);

        }

        return super.onOptionsItemSelected(item);

    }

    public void showAlertDialogButtonClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        @SuppressLint("InflateParams") final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_list, null);
        builder.setView(customLayout);
        builder.setTitle("Add List");

        builder.setPositiveButton("Make", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = customLayout.findViewById(R.id.name);

                list = new List(0, editText.getText().toString(), 0, 0, SharedPreferencesUser.getInstance(ListsActivity.this).getId());

                listManager.add(list, new VolleyCallback() {

                    @Override
                    public void onSuccess(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error")) {

                                Toast.makeText(ListsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } else {

                                list.setId(jsonObject.getInt("id"));

                            }

                        } catch(JSONException e) {

                            e.printStackTrace();

                        }

                    }

                });

                lists.add(list);

                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemInserted(lists.size() - 1);

            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onResume(){

        super.onResume();

        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cancel")
                .setMessage("Are you sure you want to leave the app ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }

                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }

                })

                .create()
                .show();

    }

}