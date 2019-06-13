package com.example.buyinglistocr.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
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

/**
 * Allow to custom the recycler view
 */
public class AdapterItems extends RecyclerView.Adapter<AdapterItems.MyViewHolder> {

    // The context of the recycler view
    private Context context;

    // The ArrayList of list
    private ArrayList<Item> items;

    // The recycler view
    private RecyclerView rv;

    /**
     * The constructor of the class
     * @param context - The context
     * @param items - The ArrayList of the list
     */
    public AdapterItems(Context context, ArrayList<Item> items, RecyclerView rv) {

        this.context = context;
        this.items = items;
        this.rv = rv;

    }

    /**
     * Allow to know the number of list
     * @return - The number of list
     */
    @Override
    public int getItemCount() {

        return items.size();

    }

    /**
     * Allow to create the view holder
     * @param parent - The parent
     * @param viewType - The view type
     * @return - The view holder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_cell, parent, false);

        return new MyViewHolder(view);

    }

    /**
     * Allow to bind the data which the view holder
     * @param holder - The view holder
     * @param position - the position in the data list
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Item item = items.get(position);
        holder.display(item, position);

    }

    /**
     * Allow to represent the view holder of a data
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // The ItemDAO
        ItemManager itemManager;

        // The Item
        private Item currentItem;

        // It position
        private int position;

        // The name of the view holder
        private final TextView name;

        // The buttons of the view holder
       // private final Button buttonModify;
        private final Button buttonDelete;
        private final Button buttonPlus;
        private CheckBox checkBox;

        /**
         * The constructor of the class
         * @param itemView - The item view
         */
        public MyViewHolder(final View itemView) {

            super(itemView);

            itemManager = new ItemManager(context);

            name = itemView.findViewById(R.id.name);

            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        currentItem.setStatus(1);
                        itemManager.update(currentItem);

                        String sampleText = currentItem.getName() + " (" + currentItem.getQuantityDesired() + "/" + currentItem.getQuantityDesired() + ") ";
                        name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        name.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                        name.setText(sampleText);
                        System.out.println("Statu : "+ itemManager.getItem(currentItem.getId()).getStatus());
                    }
                    else {
                        currentItem.setStatus(0);
                        itemManager.update(currentItem);
                        String sampleText = currentItem.getName() + " (" + currentItem.getQuantityGot() + "/" + currentItem.getQuantityDesired() + ") ";
                        name.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                        name.setTextColor(ContextCompat.getColor(context, android.R.color.black));

                        name.setText(sampleText);
                        System.out.println("Statu : "+ itemManager.getItem(currentItem.getId()).getStatus());
                    }
                }
            });

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

            //Reduce the quantity got. If the quantity got = 0 the button delete the item.
            buttonDelete = itemView.findViewById(R.id.delete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(currentItem.getQuantityGot()>0){
                        if(currentItem.getQuantityGot()==currentItem.getQuantityDesired() && currentItem.getStatus() == 1){
                            currentItem.setStatus(0);
                            checkBox.setChecked(false);
                        }
                        currentItem.setQuantityGot(currentItem.getQuantityGot()-1);
                        itemManager.update(currentItem);
                        int index = items.indexOf(currentItem);
                        rv.getAdapter().notifyItemChanged(index);
                    } else {
                        showAlertDialogSuppr(view);
                    }
                }

            });

            //Up the quantity got
            buttonPlus = itemView.findViewById(R.id.plus);
            buttonPlus.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentItem.getStatus()==1){
                        Toast toast = Toast.makeText(context, "Already max", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        if(currentItem.getQuantityGot()<currentItem.getQuantityDesired()){
                            currentItem.setQuantityGot(currentItem.getQuantityGot()+1);
                            itemManager.update(currentItem);
                        }
                        if(currentItem.getQuantityGot()==currentItem.getQuantityDesired()){
                            currentItem.setStatus(1);
                            itemManager.update(currentItem);
                        }
                        int index = items.indexOf(currentItem);
                        rv.getAdapter().notifyItemChanged(index);
                    }

                }
            }));

        }

        /**
         * Display the data in the view holder
         * @param item - The item
         */
        public void display(Item item, int position) {

            currentItem = item;
            this.position = position;
            name.setText(currentItem.getName() + " (" + currentItem.getQuantityGot() + "/" + currentItem.getQuantityDesired() + ") ");

            //gère la coche
            if(currentItem.getStatus()==1) {
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
        /**
         * The constructor of the class
         * @param itemView - The item view
         */



        }

        /**
         * Allow to define the alert dialog
         * @param view - The view
         */
        public void showAlertDialogNameClicked(View view) {

            // Create an alert builder
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Set the custom layout
           // final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_create_item, null);
            final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_modify_item,null);
            builder.setView(customLayout);

            final EditText editText = customLayout.findViewById(R.id.name);
            editText.setText(currentItem.getName());

            final EditText editTextQte = customLayout.findViewById(R.id.quantities);
            editTextQte.setText(""+currentItem.getQuantityDesired());


            builder.setTitle("Update Product");
            // Define the positive button
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

                    if(isPresent(getText, currentItem.getIdList())){
                        Toast toast = Toast.makeText(context, "This name already exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (getText.length() > 0) {

                        currentItem.setName(getText);

                    } else {

                    }

                    if (editTextQte.getText().length() > 0 && Integer.parseInt(editTextQte.getText().toString())>0) {

                        currentItem.setQuantityDesired(Integer.parseInt(editTextQte.getText().toString()));


                    } else if (editTextQte.getText().length()==0){
                        currentItem.setQuantityDesired(1);
                    }

                    itemManager.update(currentItem);

                    // TEST
                    System.out.println("TEST ADAPTERITEMS - quantityDesired : " + itemManager.getItem(currentItem.getId()).getQuantityDesired());

                    // Notify the recycler view that a data is inserted
                    rv.getAdapter().notifyItemChanged(position);

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

                    itemManager.delete(currentItem.getId());

                    int index = items.indexOf(currentItem);

                    items.remove(currentItem);

                    rv.getAdapter().notifyItemRemoved(index);

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

        /**
         * Allow to know if an item exist with the same name in this list
         * @param name - The name
         * @param idList - The list id
         * @return - True if the name exist, false else
         */
        public boolean isPresent(String name, long idList) {

            // The return value
            Boolean ret = false;

            // Get all items of our list
            ArrayList<Item> items = itemManager.get(idList);

            for(Item item : items) {

                if(item.getName().toLowerCase().equals(name.toLowerCase())) {

                    ret = true;

                }

            }

            return ret;

        }

    }

}
