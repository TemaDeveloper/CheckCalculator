package com_calculator.java_calculator.checkcalculator.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.R;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRequests extends RecyclerView.Adapter<AdapterRequests.ViewHolderRequests>{

    private Context context;
    private List<Friend> friends;

    public AdapterRequests(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolderRequests onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_request, parent, false);
        return new ViewHolderRequests(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRequests holder, int position) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> callUpdateFriend = apiInterface.updateFriend(friends.get(position).getId(), 0);

        holder.name.setText(friends.get(position).getName());

        Glide.with(holder.itemView.getContext())
                .load(friends.get(position).getImageProfile())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.noAnimation())
                .thumbnail(0.3f)
                .into(holder.imageFriendRequest);

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update friends_table ==> request = 0 => move to friend list

                callUpdateFriend.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Success YEAÄA!HH " + response.message() + " ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolderRequests extends RecyclerView.ViewHolder {

        private TextView name;
        private MaterialButton acceptButton;
        private CircleImageView imageFriendRequest;

        public ViewHolderRequests(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_view_name_friend_request);
            acceptButton = itemView.findViewById(R.id.fab_accept);
            imageFriendRequest = itemView.findViewById(R.id.image_view_friend_request);

        }
    }
}
