package com_calculator.java_calculator.checkcalculator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com_calculator.java_calculator.checkcalculator.bottomSheetAddition.BottomSheetAdditionFriendFragment;
import com_calculator.java_calculator.checkcalculator.models.Dish;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterDish;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterFriendsInTeamCheck;
import com_calculator.java_calculator.checkcalculator.adapters.RecyclerViewBuilder;
import com_calculator.java_calculator.checkcalculator.bottomSheetAddition.BottomSheetAdditionDishFragment;

public class CheckDataActivity extends AppCompatActivity implements BottomSheetAdditionDishFragment.OnInputSelected, RecyclerViewBuilder, View.OnClickListener, BottomSheetAdditionFriendFragment.OnInputSelected {
    //widgets
    private RecyclerView recyclerViewFriendsInTeamCheck, recyclerViewDishes;
    private FloatingActionButton fabAddDish;
    private ExtendedFloatingActionButton fabAddFriend;
    private ImageView back;
    //lists
    private ArrayList<Friend> friends;
    private ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);

        init();

        fabAddDish.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);
        fabAddFriend.setOnClickListener(this::onClick);

        buildRecyclerView();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_200));
        }

    }

    private void init(){
        friends = new ArrayList<>();
        dishes = new ArrayList<>();
        fabAddDish = findViewById(R.id.fab_add_dish);
        fabAddFriend = findViewById(R.id.fab_add_friend);
        back = findViewById(R.id.image_view_back);
        recyclerViewFriendsInTeamCheck = findViewById(R.id.recycler_view_friends_in_team_check);
        recyclerViewDishes = findViewById(R.id.recycler_view_dishes);
    }

    @Override
    public void buildRecyclerView() {
        //dishes
        setMyLayoutManagerAndFixedSize(recyclerViewDishes);
        //friends
        setMyLayoutManagerAndFixedSize(recyclerViewFriendsInTeamCheck);
    }

    private void setMyLayoutManagerAndFixedSize(RecyclerView rv){
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab_add_dish:
                BottomSheetAdditionDishFragment bottomSheetDialogAdditionDishFragment = new BottomSheetAdditionDishFragment();
                bottomSheetDialogAdditionDishFragment.show(getSupportFragmentManager(), "DishAdditionFragment");
                break;
            case R.id.image_view_back:
                onBackPressed();
                break;
            case R.id.fab_add_friend:
                BottomSheetAdditionFriendFragment bottomSheetAdditionFriendFragment = new BottomSheetAdditionFriendFragment();
                bottomSheetAdditionFriendFragment.show(getSupportFragmentManager(), "FriendAdditionFragment");
                break;
        }
    }

    @Override
    public void sendInput(String dish, int price) {
        dishes.add(new Dish(dish, price));
        recyclerViewDishes.setAdapter(new AdapterDish(dishes));
    }

    @Override
    public void sendInputFriend(String friendName, int totalOfFriend) {
        friends.add(new Friend(friendName));
        recyclerViewFriendsInTeamCheck.setAdapter(new AdapterFriendsInTeamCheck(friends));
    }
}