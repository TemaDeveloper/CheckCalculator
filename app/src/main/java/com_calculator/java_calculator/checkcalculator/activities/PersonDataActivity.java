package com_calculator.java_calculator.checkcalculator.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDataActivity extends AppCompatActivity {

    //Info Holders
    private String name_holder, country_holder, about_holder, image_holder, id_holder;
    //Widgets
    private TextView name, country, about;
    private ImageView userImage, userImageBackground;
    private MaterialButton followButton;
    //Api Interface
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);

        init();
        getExtras();
        setUpUri(userImage);
        setUpUri(userImageBackground);

        name.setText(name_holder);
        country.setText(country_holder);
        about.setText(about_holder);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<User> callUser = apiInterface.getUser(SharedPrefManager.getInstance(PersonDataActivity.this).getUser().getId());
                callUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Call<ResponseBody> callFriend = apiInterface.addFriend(response.body().getName(), response.body().getCountry(), response.body().getAbout(), response.body().getImageProfile(),
                                SharedPrefManager.getInstance(PersonDataActivity.this).getUser().getId(),
                                Integer.parseInt(id_holder), 1, name_holder, image_holder);
                        callFriend.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                followButton.setBackgroundColor(getResources().getColor(R.color.white));
                                followButton.setTextColor(getResources().getColor(R.color.purple_200));
                                followButton.setText("Followed");
                                Toast.makeText(PersonDataActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("FAILED_FOLLOWED_USER", t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });


            }
        });

        checkFollowed();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_200));
        }

    }

    private void checkFollowed() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        int idOfUser = Integer.parseInt(id_holder);
        int id = SharedPrefManager.getInstance(PersonDataActivity.this).getUser().getId();
        Call<List<Friend>> friendCall = apiInterface.getFriends(id, id);
        friendCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                List<Friend> friends = response.body();
                for(Friend friend : friends){
                    if((idOfUser == friend.getMy_id() && id == friend.getUser_id()) || (idOfUser == friend.getUser_id() && id == friend.getMy_id())){
                        followButton.setBackgroundColor(getResources().getColor(R.color.white));
                        followButton.setTextColor(getResources().getColor(R.color.purple_200));
                        followButton.setText("Followed");
                        Log.d("ID_TAG_F", friend.getMy_id() + " - my_id" + " ID WE GO INTENT " + idOfUser);
                        Log.d("ID_TAG_F", friend.getUser_id() + " - user_id");
                    }else{
                        followButton.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        followButton.setTextColor(getResources().getColor(R.color.white));
                        followButton.setText("Follow");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
            }
        });
    }

    private void getExtras() {
        name_holder = getIntent().getStringExtra("name");
        id_holder = getIntent().getStringExtra("id");
        country_holder = getIntent().getStringExtra("country");
        about_holder = getIntent().getStringExtra("about");
        image_holder = getIntent().getStringExtra("image");
    }

    private void init() {
        name = findViewById(R.id.text_view_name_friend);
        country = findViewById(R.id.text_view_country);
        about = findViewById(R.id.text_view_about);
        userImage = findViewById(R.id.image_view_user);
        userImageBackground = findViewById(R.id.image_view_user_background);
        followButton = findViewById(R.id.button_follow);
    }

    private void setUpUri(ImageView image) {
        Glide.with(PersonDataActivity.this)
                .load(image_holder)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.noAnimation())
                .thumbnail(0.3f)
                .into(image);
    }

}