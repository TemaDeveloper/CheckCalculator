package com_calculator.java_calculator.checkcalculator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.R;

public class AdapterFriendsInTeamCheck extends RecyclerView.Adapter<AdapterFriendsInTeamCheck.ViewHolderFriendsInTeamCheck> {

    private ArrayList<Friend> friends;

    public AdapterFriendsInTeamCheck(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolderFriendsInTeamCheck onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_in_team_check, parent, false);
        return new ViewHolderFriendsInTeamCheck(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFriendsInTeamCheck holder, int position) {
        holder.friendNameInCheck.setText(friends.get(position).getName());

        if(position == (getItemCount()-1)){
            holder.viewLine.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolderFriendsInTeamCheck extends RecyclerView.ViewHolder {
        private TextView friendNameInCheck;
        private View viewLine;
        public ViewHolderFriendsInTeamCheck(@NonNull View itemView) {
            super(itemView);

            friendNameInCheck = itemView.findViewById(R.id.text_view_friend_name_in_check);
            viewLine = itemView.findViewById(R.id.view_line);

        }
    }
}
