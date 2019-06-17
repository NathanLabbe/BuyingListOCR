package com.example.buyinglistocr.controller;

import android.annotation.SuppressLint;
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

        private final Button buttonAdd;
        private final Button buttonSub;

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
                public void onClick(View view) {

                    showAlertDialogButtonClicked();

                }

            });

            name.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Delete")

                            .setMessage("Are you sure ?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    itemManager.delete(item.getId());

                                    items.remove(item);

                                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

                                }

                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { }

                            })

                            .create()
                            .show();

                    return true;

                }
            });

            buttonAdd = itemView.findViewById(R.id.add);
            buttonAdd.setOnClickListener((new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(item.getQuantityGot() < item.getQuantityDesired()){

                        item.setQuantityGot(item.getQuantityGot() + 1);
                        itemManager.update(item);

                        if(item.getQuantityGot() == item.getQuantityDesired()) {

                            item.setStatus(1);
                            itemManager.update(item);

                        }

                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(items.indexOf(item));

                    } else {

                        Toast.makeText(context, "Already max", Toast.LENGTH_LONG).show();

                    }

                }

            }));

            buttonSub = itemView.findViewById(R.id.sub);
            buttonSub.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(item.getQuantityGot() > 0){

                        if(item.getQuantityGot() == item.getQuantityDesired()){

                            item.setStatus(0);
                            checkBox.setChecked(false);

                        }

                        item.setQuantityGot(item.getQuantityGot() - 1);
                        itemManager.update(item);

                        Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(items.indexOf(item));

                    } else {

                        Toast.makeText(context, "Already null", Toast.LENGTH_LONG).show();

                    }

                }

            });

        }

        void display(Item item, int position) {

            this.item = item;
            this.position = position;

            name.setText(this.item.getName() + " (" + this.item.getQuantityGot() + "/" + this.item.getQuantityDesired() + ") ");

            if(this.item.getStatus() == 1) {

                checkBox.setChecked(true);

                String sampleText = name.getText().toString();

                name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                name.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                name.setText(sampleText);

            } else {

                String sampleText = name.getText().toString();

                name.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                name.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                name.setText(sampleText);

            }

        }

        void showAlertDialogButtonClicked() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            @SuppressLint("InflateParams") final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
            builder.setView(customLayout);
            builder.setTitle("Update Product");

            final EditText editTextName = customLayout.findViewById(R.id.name);
            final EditText editTextQuantity = customLayout.findViewById(R.id.quantities);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    item.setName(editTextName.getText().toString());
                    item.setQuantityDesired(Integer.parseInt(editTextQuantity.getText().toString()));
                    item.setQuantityGot(0);

                    itemManager.update(item);

                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(position);

                }

            });

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            editTextName.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    /**

                     if(editText.getText().length()<1){
                     dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                     Toast toast = Toast.makeText(context, "You need to choose a name", Toast.LENGTH_SHORT);
                     toast.show();
                     } else if (!editText.getText().toString().startsWith(" ") && (editTextQte.getText().length()<1 || Integer.parseInt(editTextQte.getText().toString()) > 0)){
                     dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                     }


                     **/


                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if(editTextName.length() == 0 || editTextQuantity.length() == 0) {

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                    } else {

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                    }

                }

            });

            editTextQuantity.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    /**

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

                     **/

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if(editTextName.length() == 0 || editTextQuantity.length() == 0) {

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                    } else {

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                    }

                }

            });

        }

    }

}
