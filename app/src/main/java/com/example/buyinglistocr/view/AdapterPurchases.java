package com.example.buyinglistocr.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.Tmp.SavePurchase;
import com.example.buyinglistocr.controller.ItemsActivity;

import java.util.ArrayList;

/**
 * Allow to custom the recycler view
 */
public class AdapterPurchases extends RecyclerView.Adapter<AdapterPurchases.MyViewHolder> {

    // The context of the recycler view
    private Context context;

    // The ArrayList of list
    private ArrayList<SavePurchase> savedPurchases;

    // The recycler view
    private RecyclerView rv;


    /**
     * The constructor of the class
     * @param context - The context
     * @param savedPurchases - The ArrayList of SavedPurchases
     */
    public AdapterPurchases(Context context, ArrayList<SavePurchase> savedPurchases, RecyclerView rv) {

        this.context = context;
        this.savedPurchases = savedPurchases;
        this.rv = rv;

    }


    @NonNull
    @Override
    public AdapterPurchases.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPurchases.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    /**
     * Allow to represent the view holder of a data
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // The pair that the view holder represent
        private SavePurchase currentSavePurchase;

        // The name of the view holder
        private final TextView name;

        /**
         * The constructor of the class
         * @param itemView - The item view
         */
        public MyViewHolder(final View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    // Start the new activity with the current list that parameter
                    Intent intent = new Intent(context, ItemsActivity.class);
                    intent.putExtra("savePurchase", currentSavePurchase);
                    context.startActivity(intent);

                }

            });

        }

        /**
         * Display the data in the view holder
         * @param savePurchase - The savePurchase
         */
        public void display(SavePurchase savePurchase) {

            currentSavePurchase = savePurchase;
            name.setText(currentSavePurchase.getName());

        }

    }
}
