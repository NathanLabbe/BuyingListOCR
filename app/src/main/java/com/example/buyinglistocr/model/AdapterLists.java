package com.example.buyinglistocr.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.controller.ListView;

import java.util.ArrayList;

/**
 * Allow to custom the recycler view
 */
public class AdapterLists extends RecyclerView.Adapter<AdapterLists.MyViewHolder> {

    // The context of the recycler view
    private Context context;

    // The ArrayList of list
    private ArrayList<List> lists;

    /**
     * The constructor of the class
     * @param context - The context
     * @param lists - The ArrayList of the list
     */
    public AdapterLists(Context context, ArrayList<List> lists) {

        this.context = context;
        this.lists = lists;

    }

    /**
     * Allow to know the number of list
     * @return - The number of list
     */
    @Override
    public int getItemCount() {

        return lists.size();

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

        View view = inflater.inflate(R.layout.card_cell, parent, false);

        return new MyViewHolder(view);

    }

    /**
     * Allow to bind the data which the view holder
     * @param holder - The view holder
     * @param position - the position in the data list
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        List list = lists.get(position);
        holder.display(list);

    }

    /**
     * Allow to represent the view holder of a data
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // The pair that the view holder represent
        private List currentList;

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
                    Intent intent = new Intent(context, ListView.class);
                    intent.putExtra("list", currentList);
                    context.startActivity(intent);

                }

            });

        }

        /**
         * Display the data in the view holder
         * @param list - The list
         */
        public void display(List list) {

            currentList = list;
            name.setText(currentList.getName());

        }

    }

}
