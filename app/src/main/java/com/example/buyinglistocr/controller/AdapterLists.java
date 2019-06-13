package com.example.buyinglistocr.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.util.SharedPreferencesList;
import com.example.buyinglistocr.util.SharedPreferencesUser;

import java.util.ArrayList;

public class AdapterLists extends RecyclerView.Adapter<AdapterLists.MyViewHolder> {

    private Context context;

    private ArrayList<List> lists;

    public AdapterLists(Context context, ArrayList<List> lists) {

        this.context = context;
        this.lists = lists;

    }

    @Override
    public int getItemCount() {

        return lists.size();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_cell, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        List list = lists.get(position);
        holder.display(list);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private List list;

        private final TextView textViewName;

        public MyViewHolder(final View itemView) {

            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SharedPreferencesList.getInstance(context).listChoose(list.getId());

                    Intent intent = new Intent(context, ListView.class);
                    context.startActivity(intent);

                }

            });

        }

        public void display(List list) {

            this.list = list;
            textViewName.setText(this.list.getName());

        }

    }

}
