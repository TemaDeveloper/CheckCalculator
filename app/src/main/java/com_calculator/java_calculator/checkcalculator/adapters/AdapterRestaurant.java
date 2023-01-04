package com_calculator.java_calculator.checkcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.models.Restaurant;

public class AdapterRestaurant extends RecyclerView.Adapter<AdapterRestaurant.ViewHolderRestaurant> {

    private Context context;
    private ArrayList<Restaurant> restaurants;

    public AdapterRestaurant(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolderRestaurant onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolderRestaurant(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRestaurant holder, int position) {
        Restaurant item = restaurants.get(position);
        holder.title.setText("#" + item.getTitle());
        holder.rating.setText(item.getRating()+"x");
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolderRestaurant extends RecyclerView.ViewHolder {

        private TextView title, rating;

        public ViewHolderRestaurant(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_view_title);
            rating = itemView.findViewById(R.id.text_view_rating);

        }
    }
}
