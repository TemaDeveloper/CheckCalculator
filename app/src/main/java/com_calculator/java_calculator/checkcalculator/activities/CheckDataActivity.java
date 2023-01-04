package com_calculator.java_calculator.checkcalculator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.models.Dish;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterDish;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterFriendsInTeamCheck;
import com_calculator.java_calculator.checkcalculator.adapters.RecyclerViewBuilder;
import com_calculator.java_calculator.checkcalculator.bottomSheetAddition.BottomSheetAdditionDishFragment;

public class CheckDataActivity extends AppCompatActivity implements BottomSheetAdditionDishFragment.OnInputSelected, RecyclerViewBuilder {
    //widgets
    private RecyclerView recyclerViewFriendsInTeamCheck, recyclerViewDishes;
    private FloatingActionButton fabAddDish;
    //lists
    private ArrayList<Friend> friends;
    private ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);

        init();

        fabAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetAdditionDishFragment bottomSheetDialogAdditionDishFragment = new BottomSheetAdditionDishFragment();
                bottomSheetDialogAdditionDishFragment.show(getSupportFragmentManager(), "DishAdditionFragment");
            }
        });

        friends.add(new Friend("Dima"));
        friends.add(new Friend("Roma"));
        friends.add(new Friend("Sia"));
        friends.add(new Friend("Arina"));
        friends.add(new Friend("Elya"));

        dishes.add(new Dish("Meat", "341$"));
        dishes.add(new Dish("Meat", "102$"));
        dishes.add(new Dish("Cupcake", "98$"));
        dishes.add(new Dish("Tea", "4$"));

        buildRecyclerView();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_200));
        }

    }

    @Override
    public void sendInput(String dish) {

    }

    private void init(){
        friends = new ArrayList<>();
        dishes = new ArrayList<>();
        fabAddDish = findViewById(R.id.fab_add_dish);
        recyclerViewFriendsInTeamCheck = findViewById(R.id.recycler_view_friends_in_team_check);
        recyclerViewDishes = findViewById(R.id.recycler_view_dishes);
    }

    @Override
    public void buildRecyclerView() {
        //dishes
        setMyLayoutManagerAndFixedSize(recyclerViewDishes);
        recyclerViewDishes.setAdapter(new AdapterDish(dishes));
        //friends
        setMyLayoutManagerAndFixedSize(recyclerViewFriendsInTeamCheck);
        recyclerViewFriendsInTeamCheck.setAdapter(new AdapterFriendsInTeamCheck(friends));
    }

    private void setMyLayoutManagerAndFixedSize(RecyclerView rv){
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}