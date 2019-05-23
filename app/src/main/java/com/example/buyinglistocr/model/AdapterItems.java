package com.example.buyinglistocr.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.buyinglistocr.R;

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
        ItemDAO itemDAO;

        // The Item
        private Item currentItem;

        // It position
        private int position;

        // The name of the view holder
        private final TextView name;

        // The buttons of the view holder
       // private final Button buttonModify;
        private final Button buttonDelete;

        /**
         * The constructor of the class
         * @param itemView - The item view
         */
        public MyViewHolder(final View itemView) {

            super(itemView);

            itemDAO = new ItemDAO(context);

            name = itemView.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialogNameClicked(v);
                }
            });

        //  buttonModify = itemView.findViewById(R.id.buttonModify);
        //  buttonModify.setOnClickListener(new View.OnClickListener() {

        //      @Override
        //      public void onClick(View view) {

        //          showAlertDialogButtonClicked(view);

        //      }

        //  });

            buttonDelete = itemView.findViewById(R.id.delete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    itemDAO.delete(currentItem.getId());

                    int index = items.indexOf(currentItem);

                    items.remove(currentItem);

                    rv.getAdapter().notifyItemRemoved(index);

                }

            });

        }

        /**
         * Display the data in the view holder
         * @param item - The item
         */
        public void display(Item item, int position) {

            currentItem = item;
            this.position = position;
            name.setText(currentItem.getName());

        }

        /**
         * Allow to define the alert dialog
         * @param view - The view
         */
        public void showAlertDialogNameClicked(View view) {

            // Create an alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Set the custom layout
           // final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_create_item, null);
            final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_modify_item,null);
            builder.setView(customLayout);

            final EditText editText = customLayout.findViewById(R.id.name);
            editText.setText(currentItem.getName());

            // Define the positive button
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    currentItem.setName(editText.getText().toString());

                    itemDAO.update(currentItem);

                    // Notify the recycler view that a data is inserted
                    rv.getAdapter().notifyItemChanged(position);

                }

            });

            // Create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }


    }

}
