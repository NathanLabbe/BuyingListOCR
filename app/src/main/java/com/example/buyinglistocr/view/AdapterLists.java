package com.example.buyinglistocr.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.controller.ItemsActivity;
import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.model.SharedPreferencesList;

import java.util.ArrayList;

public class AdapterLists extends RecyclerView.Adapter<AdapterLists.MyViewHolder> {

    private Context context;

    private RecyclerView rv;

    private ArrayList<List> lists;

    public AdapterLists(Context context, ArrayList<List> lists) {

        this.context = context;
        this.lists = lists;


    }

    @Override
    public int getItemCount() {

        return lists.size();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_cell, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        List list = lists.get(position);
        holder.display(list);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private List list;

        private final TextView textViewName;

        MyViewHolder(final View itemView) {

            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SharedPreferencesList.getInstance(context).setList(list);

                    Intent intent = new Intent(context, ItemsActivity.class);
                    context.startActivity(intent);

                }

            });

        }

        void display(List list) {

            this.list = list;
            textViewName.setText(this.list.getName());

        }

    }

}
