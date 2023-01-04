package com_calculator.java_calculator.checkcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.Friend;

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.ViewHolderFriends> {

    private Context context;
    private List<Friend> friends;

    public AdapterFriends(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public AdapterFriends.ViewHolderFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new AdapterFriends.ViewHolderFriends(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFriends.ViewHolderFriends holder, int position) {

        Friend item = friends.get(position);

        if (item.getUser_id() == SharedPrefManager.getInstance(context).getUser().getId()) {
            holder.name.setText("#" + item.getName());
            Glide.with(holder.itemView.getContext())
                    .load(friends.get(position).getImageProfile())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.noAnimation())
                    .thumbnail(0.3f)
                    .into(holder.imageProfile);

        } else {

            holder.name.setText("#" + item.getFriendName());
            Glide.with(holder.itemView.getContext())
                    .load(item.getFriendImage())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.noAnimation())
                    .thumbnail(0.3f)
                    .into(holder.imageProfile);

        }


    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolderFriends extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageProfile;

        public ViewHolderFriends(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_view_friend_name);
            imageProfile = itemView.findViewById(R.id.image_friend_image);

        }
    }
}
