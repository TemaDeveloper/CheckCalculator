package com_calculator.java_calculator.checkcalculator.bottomSheetAddition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetAdditionFriendFragment extends BottomSheetDialogFragment {

    private static final String TAG = "FriendAdditionFragment";
    public OnInputSelected mOnInputSelected;
    private ExtendedFloatingActionButton cancel, addFriend;
    private AutoCompleteTextView friendTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_addition_friend, container, false);
        init(view);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Friend>> friendsCall = apiInterface.getFriends(SharedPrefManager.getInstance(getContext()).getUser().getId(), SharedPrefManager.getInstance(getContext()).getUser().getId());
        friendsCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                ArrayList<String> names = new ArrayList<>();
                for(Friend friend : response.body()){
                    names.add(friend.getName());
                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, names);
                friendTitle.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friend_holder = friendTitle.getText().toString().trim();
                if(!friend_holder.equals(null)){
                    mOnInputSelected.sendInputFriend(friend_holder, 0);
                    getDialog().dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void init(View view){
        addFriend = view.findViewById(R.id.button_add_friend);
        cancel = view.findViewById(R.id.button_cancel);
        friendTitle = view.findViewById(R.id.autoCompleteFriend);
    }

    public interface OnInputSelected {
        void sendInputFriend(String friendName, int totalOfFriend);
    }

}
