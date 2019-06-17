package com.example.buyinglistocr.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.Item;
import com.example.buyinglistocr.model.ItemManager;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.MyViewHolder> {

    private Context context;

    private ArrayList<Item> items;

    private RecyclerView recyclerView;

    AdapterItems(Context context, ArrayList<Item> items, RecyclerView recyclerView) {

        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;

    }

    @Override
    public int getItemCount() {

        return items.size();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_cell, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = items.get(position);
        holder.display(item, position);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemManager itemManager;

        private Item item;

        private int position;

        private CheckBox checkBox;

        private final TextView name;

        private final Button buttonDelete;
        private final Button buttonPlus;

        MyViewHolder(final View itemView) {

            super(itemView);

            itemManager = new ItemManager(context);

            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(checkBox.isChecked()){

                        item.setStatus(1);
                        item.setQuantityGot(item.getQuantityDesired());

                        itemManager.update(item);

                        String sampleText = item.getName() + " (" + item.getQuantityDesired() + "/" + item.getQuantityDesired() + ") ";

                        name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        name.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                        name.setText(sampleText);

                    } else {

                        item.setStatus(0);
                        item.setQuantityGot(0);

                        itemManager.update(item);

                        String sampleText = item.getName() + " (" + item.getQuantityGot() + "/" + item.getQuantityDesired() + ") ";

                        name.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                        name.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                        name.setText(sampleText);

                    }

                }

            });

            name = itemView.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    showAlertDialogNameClicked(v);

                }

            });

            name.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    showAlertDialogSuppr(v);

                    return true;

                }
            });

            buttonDelete = itemView.findViewById(R.id.sub);
            buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(item.getQuantityGot() > 0){

                        if(item.getQuantityGot()==item.getQuantityDesired() && item.getStatus() == 1){

                            item.setStatus(0);
                            checkBox.setChecked(false);

                        }

                        item.setQuantityGot(item.getQuantityGot()-1);
                        itemManager.update(item);
                        int index = items.indexOf(item);

                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(index);

                    } else {

                        showAlertDialogSuppr(view);

                    }

                }

            });

            buttonPlus = itemView.findViewById(R.id.add);
            buttonPlus.setOnClickListener((new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(item.getStatus() == 1){

                        Toast.makeText(context, "Already max", Toast.LENGTH_LONG).show();

                    } else {

                        if(item.getQuantityGot() < item.getQuantityDesired()){

                            item.setQuantityGot(item.getQuantityGot() + 1);
                            itemManager.update(item);

                        }

                        if(item.getQuantityGot()==item.getQuantityDesired()){

                            item.setStatus(1);
                            itemManager.update(item);

                        }

                        int index = items.indexOf(item);
                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(index);

                    }

                }

            }));

        }

        public void display(Item item, int position) {

            this.item = item;
            this.position = position;
            name.setText(this.item.getName() + " (" + this.item.getQuantityGot() + "/" + this.item.getQuantityDesired() + ") ");

            if(this.item.getStatus()==1) {
                checkBox.setChecked(true);
                String sampleText = name.getText().toString();
                name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                name.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                name.setText(sampleText);
            }else{
                String sampleText = name.getText().toString();
                name.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                name.setTextColor(ContextCompat.getColor(context, android.R.color.black));

                name.setText(sampleText);
            }



        }

        public void showAlertDialogNameClicked(View view) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Set the custom layout
           // final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
            final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_item,null);
            builder.setView(customLayout);

            final EditText editText = customLayout.findViewById(R.id.name);
            editText.setText(item.getName());

            final EditText editTextQte = customLayout.findViewById(R.id.quantities);
            editTextQte.setText(""+item.getQuantityDesired());


            builder.setTitle("Update Product");

            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String tmp = editText.getText().toString();
                    int index = tmp.length()-1;

                    for (int i = index; i>0; i--){
                        if(tmp.charAt(i) == ' ') {
                            index--;
                        } else break;
                    }
                    final String getText = tmp.substring(0, index+1);

                    /*if(isPresent(getText, item.getIdList())){
                        Toast toast = Toast.makeText(context, "This name already exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (getText.length() > 0) {

                        item.setName(getText);

                    } else {

                    }*/

                    if (editTextQte.getText().length() > 0 && Integer.parseInt(editTextQte.getText().toString())>0) {

                        item.setQuantityDesired(Integer.parseInt(editTextQte.getText().toString()));


                    } else if (editTextQte.getText().length()==0){
                        item.setQuantityDesired(1);
                    }

                    itemManager.update(item);

                    // TEST
                    System.out.println("TEST ADAPTERITEMS - quantityDesired : " + itemManager.getItem(item.getId()).getQuantityDesired());

                    // Notify the recycler view that a data is inserted
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(position);

                }

            });

            // Create and show the alert dialog
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(editText.getText().length()<1){
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        Toast toast = Toast.makeText(context, "You need to choose a name", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (!editText.getText().toString().startsWith(" ")  && (editTextQte.getText().length()<1 || Integer.parseInt(editTextQte.getText().toString()) > 0)){
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editTextQte.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(editTextQte.getText().length()>0) {
                        if (Integer.parseInt(editTextQte.getText().toString()) <= 0) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            Toast toast = Toast.makeText(context, "Impossible to set a quantity at 0", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (editText.getText().length()>1 && !editText.getText().toString().startsWith(" ")) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }
                    } else if (editText.getText().length()>1 && !editText.getText().toString().startsWith(" ")){
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        public void showAlertDialogSuppr(View view) {
            // Create an alert builder
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Product");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    checkBox.setChecked(false);
                    String sampleText = name.getText().toString();
                    name.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                    name.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                    name.setText(sampleText);

                    itemManager.delete(item.getId());

                    int index = items.indexOf(item);

                    items.remove(item);

                    recyclerView.getAdapter().notifyItemRemoved(index);

                }

            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Nothing
                }
            });

            final AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

}
