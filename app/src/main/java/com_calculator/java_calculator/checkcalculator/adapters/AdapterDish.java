package com_calculator.java_calculator.checkcalculator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.models.Dish;
import com_calculator.java_calculator.checkcalculator.R;

public class AdapterDish extends RecyclerView.Adapter<AdapterDish.ViewHolderDish> {

    private ArrayList<Dish> dishes;

    public AdapterDish(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public ViewHolderDish onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new ViewHolderDish(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDish holder, int position) {
        holder.dishName.setText(dishes.get(position).getDishName());
        holder.dishPrice.setText(dishes.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class ViewHolderDish extends RecyclerView.ViewHolder {

        private TextView dishName, dishPrice;

        public ViewHolderDish(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.text_view_dish_name);
            dishPrice = itemView.findViewById(R.id.text_view_dish_price);

        }
    }
}
