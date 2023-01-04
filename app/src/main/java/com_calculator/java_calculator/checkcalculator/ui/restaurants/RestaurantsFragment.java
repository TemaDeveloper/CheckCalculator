package com_calculator.java_calculator.checkcalculator.ui.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import com_calculator.java_calculator.checkcalculator.adapters.AdapterRestaurant;
import com_calculator.java_calculator.checkcalculator.databinding.FragmentRestaurantsBinding;
import com_calculator.java_calculator.checkcalculator.models.Restaurant;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.adapters.RecyclerViewBuilder;

public class RestaurantsFragment extends Fragment implements RecyclerViewBuilder {

    //ViewBinding
    private FragmentRestaurantsBinding binding;
    //widgets
    private TextView ethicsText;
    private RecyclerView recyclerViewRestaurants;
    //lists
    private ArrayList<Restaurant> restaurants;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRestaurantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);
        insertArrayLists();
        buildRecyclerView();
        receiveNewEthics();

        return root;
    }

    private void insertArrayLists(){
        //restaurant
        restaurants.add(new Restaurant("Am!Bar.", 4, 0));
        restaurants.add(new Restaurant("Parmezan", 3, 0));
        restaurants.add(new Restaurant("Cafe coffee only here", 3, 0));
        restaurants.add(new Restaurant("Shaurma Bistro", 5, 0));
        restaurants.add(new Restaurant("NicePriceCafe", 5, 0));
    }

    private void receiveNewEthics(){
        String[] array = getContext().getResources().getStringArray(R.array.restaurant_ethics);
        String ethics = array[new Random().nextInt(array.length)];
        ethicsText.setText(ethics);
    }

    private void init(View root){
        //widgets
        recyclerViewRestaurants = root.findViewById(R.id.recycler_view_horizontal_restaurants);
        ethicsText = root.findViewById(R.id.text_view_ethics);
        //arrayList
        restaurants = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void buildRecyclerView() {
        //restaurants
        recyclerViewRestaurants.setHasFixedSize(true);
        recyclerViewRestaurants.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRestaurants.setAdapter(new AdapterRestaurant(getContext(), restaurants));
    }
}