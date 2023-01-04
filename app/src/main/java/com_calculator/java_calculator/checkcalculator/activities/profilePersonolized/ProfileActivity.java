package com_calculator.java_calculator.checkcalculator.activities.profilePersonolized;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.activities.MainActivity;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //Widgets
    private TextView logOut, name, email, country, about;
    private ImageView settings, profileImage, backgroundImage, back;
    //response holder
    String imageResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

        findViewById(R.id.button_update).setOnClickListener(this::onClick);
        logOut.setOnClickListener(this::onClick);
        settings.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);

        getSavedUser(ProfileActivity.this);

        //set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_200));
        }

    }

    private void getSavedUser(Context context) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> userCall = apiInterface.getUser(SharedPrefManager.getInstance(context).getUser().getId());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    name.setText(response.body().getName());
                    email.setText(response.body().getEmail());
                    country.setText(response.body().getCountry());
                    about.setText(response.body().getAbout());

                    imageResponse = response.body().getImageProfile();
                    Uri imageUri = Uri.parse(imageResponse);

                    setUpUri(imageUri, profileImage);
                    setUpUri(imageUri, backgroundImage);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG_ID_IMAGE_SAVED", "FAILED " + t.getMessage());
            }
        });
    }

    private void setUpUri(Uri uri, ImageView image){
        Glide.with(ProfileActivity.this)
                .load(uri)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.noAnimation())
                .thumbnail(0.3f)
                .into(image);
    }

    private void init() {
        logOut = findViewById(R.id.text_view_log_out);
        name = findViewById(R.id.text_view_name);
        email = findViewById(R.id.text_view_email);
        country = findViewById(R.id.text_view_country);
        about = findViewById(R.id.text_view_about);
        settings = findViewById(R.id.image_view_settings);
        profileImage = findViewById(R.id.image_view_profile);
        backgroundImage = findViewById(R.id.image_view_profile_background);
        back = findViewById(R.id.image_view_back);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_update) {
            startActivity(new Intent(ProfileActivity.this, UpdateActivity.class)
                    .putExtra("name", name.getText().toString())
                    .putExtra("email", email.getText().toString())
                    .putExtra("country", country.getText().toString())
                    .putExtra("about", about.getText().toString())
                    .putExtra("image", imageResponse));
        } else if (view.getId() == R.id.text_view_log_out) {
            SharedPrefManager.getInstance(ProfileActivity.this).logout();
        } else if(view.getId() == R.id.image_view_settings){
            BottomSheetSettingsFragment bottomSheetInfoFragment = new BottomSheetSettingsFragment();
            bottomSheetInfoFragment.show(getSupportFragmentManager(), "MyCustomDialog");
        }else if(view.getId() == R.id.image_view_back){
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        }
    }
}