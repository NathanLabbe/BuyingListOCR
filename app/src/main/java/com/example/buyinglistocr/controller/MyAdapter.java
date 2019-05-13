package com.example.buyinglistocr.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyinglistocr.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> test;

    public MyAdapter(ArrayList<String> test) {

        this.test = test;

    }

    @Override
    public int getItemCount() {

        return test.size();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_cell, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.display(test.get(position));

    }

    public List<String> getTest() {

        return this.test;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        public MyViewHolder(final View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.name);

        }

        public void display(String test) {

            name.setText(test);

        }

    }

}
