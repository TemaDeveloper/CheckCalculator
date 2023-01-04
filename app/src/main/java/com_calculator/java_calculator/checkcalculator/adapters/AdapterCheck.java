package com_calculator.java_calculator.checkcalculator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.models.Check;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.activities.CheckDataActivity;

public class AdapterCheck extends RecyclerView.Adapter<AdapterCheck.ViewHolderCheck> {

    private Context context;
    private ArrayList<Check> checks;

    public AdapterCheck(Context context, ArrayList<Check> checks) {
        this.context = context;
        this.checks = checks;
    }

    @NonNull
    @Override
    public ViewHolderCheck onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check, parent,false);
        return new ViewHolderCheck(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCheck holder, int position) {

        Check item = checks.get(position);
        holder.restaurantTitle.setText(item.getRestaurantTitle());
        holder.date.setText(item.getDate());

        if(item.getDate().equals("Today")){
            holder.cardViewCheck.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));
            holder.date.setTextColor(context.getResources().getColor(R.color.white));
            holder.restaurantTitle.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CheckDataActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolderCheck extends RecyclerView.ViewHolder {

        private TextView restaurantTitle, date;
        private CardView cardViewCheck;

        public ViewHolderCheck(@NonNull View itemView) {
            super(itemView);

            restaurantTitle = itemView.findViewById(R.id.text_view_restaurant_title);
            date = itemView.findViewById(R.id.text_view_date);
            cardViewCheck = itemView.findViewById(R.id.card_view_check);

        }
    }
}
