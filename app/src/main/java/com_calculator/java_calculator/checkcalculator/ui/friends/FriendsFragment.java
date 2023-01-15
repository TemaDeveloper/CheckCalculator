package com_calculator.java_calculator.checkcalculator.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterFriends;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterRequests;
import com_calculator.java_calculator.checkcalculator.adapters.RecyclerViewBuilder;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.databinding.FragmentFriendsBinding;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment implements RecyclerViewBuilder {

    //binding
    private FragmentFriendsBinding binding;
    //Widgets
    private RecyclerView recyclerViewFriends, recyclerViewFriendsRequests;
    private LinearLayout linearLayoutAnimation;
    //Lists
    private List<Friend> requests;
    private List<Friend> friends;
    //RecyclerView adapter
    private AdapterFriends adapterFriends;
    //Api interface
    private ApiInterface apiInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);

        recyclerViewFriends.setHasFixedSize(true);
        recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        buildRecyclerView();

        return root;
    }


    private void init(View root) {
        requests = new ArrayList<>();
        recyclerViewFriends = root.findViewById(R.id.recycler_view_friends);
        linearLayoutAnimation = root.findViewById(R.id.linear_layout_animation);
        recyclerViewFriendsRequests = root.findViewById(R.id.recycler_view_friends_request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void buildRecyclerView() {

        recyclerViewFriendsRequests.setHasFixedSize(true);
        recyclerViewFriendsRequests.setLayoutManager(new LinearLayoutManager(getContext()));

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Friend>> callRequests = apiInterface.getRequests(SharedPrefManager.getInstance(getContext()).getUser().getId());
        callRequests.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                requests = response.body();
                if ((requests != null ? requests.size() : 0) == 0) {
                    linearLayoutAnimation.setVisibility(View.VISIBLE);
                    recyclerViewFriendsRequests.setVisibility(View.GONE);
                } else {
                    linearLayoutAnimation.setVisibility(View.GONE);
                    recyclerViewFriendsRequests.setVisibility(View.VISIBLE);
                    recyclerViewFriendsRequests.setAdapter(new AdapterRequests(getContext(), requests));
                }

            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d("Requests_TAG", t.getMessage());
            }
        });

        Call<List<Friend>> callFriends = apiInterface.getFriends(SharedPrefManager.getInstance(getContext()).getUser().getId(), SharedPrefManager.getInstance(getContext()).getUser().getId());
        callFriends.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                friends = response.body();
                Collections.shuffle(friends);
                recyclerViewFriends.setAdapter(new AdapterFriends(getContext(), friends));
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d("Requests_TAG", t.getMessage());
            }
        });

    }
}