package com_calculator.java_calculator.checkcalculator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.activities.PersonDataActivity;
import com_calculator.java_calculator.checkcalculator.models.Check;
import com_calculator.java_calculator.checkcalculator.models.ItemUserTeamCheck;
import com_calculator.java_calculator.checkcalculator.models.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public List<ItemUserTeamCheck> exampleListFull;
    private List<ItemUserTeamCheck> items;
    private Context context;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemUserTeamCheck> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemUserTeamCheck item : exampleListFull) {
                    if (item.getType() == 0) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }else{
                        if (item.getTitleOfRest().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            if(items != null){
                items.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };


    public AdapterMain(List<ItemUserTeamCheck> items, Context context) {
        this.items = items;
        this.context = context;
        exampleListFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new UserViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user, parent, false)
            );
        } else {
            return new CheckViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_check, parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            User user = (User) items.get(position).getObject();
            ((UserViewHolder) holder).setUserData(user, context);
        } else {
            Check check = (Check) items.get(position).getObject();
            ((CheckViewHolder) holder).setCheckData(check);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        CircleImageView userImage;

        UserViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text_view_user_name);
            userImage = itemView.findViewById(R.id.image_user);
        }

        void setUserData(User user, Context context) {
            textView1.setText(user.getName());
            Glide.with(itemView.getContext())
                    .load(user.getImageProfile())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.noAnimation())
                    .thumbnail(0.3f)
                    .into(userImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, PersonDataActivity.class)
                            .putExtra("id", user.getId() + "")
                            .putExtra("name", user.getName())
                            .putExtra("country", user.getCountry())
                            .putExtra("about", user.getAbout())
                            .putExtra("image", user.getImageProfile())
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

        }

    }

    static class CheckViewHolder extends RecyclerView.ViewHolder {
        TextView checkTitle;

        CheckViewHolder(View itemView) {
            super(itemView);
            checkTitle = itemView.findViewById(R.id.text_view_check_title);
        }

        void setCheckData(Check check) {
            checkTitle.setText(check.getRestaurantTitle());
        }
    }
}
