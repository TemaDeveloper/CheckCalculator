package com_calculator.java_calculator.checkcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com_calculator.java_calculator.checkcalculator.activities.profilePersonolized.ProfileActivity;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterMain;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.Language;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.databinding.ActivityMainBinding;
import com_calculator.java_calculator.checkcalculator.models.Check;
import com_calculator.java_calculator.checkcalculator.models.ItemUserTeamCheck;
import com_calculator.java_calculator.checkcalculator.models.User;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Language {

    //binding
    private ActivityMainBinding binding;
    //widgets
    private Toolbar mainToolbar;
    private CircleImageView userImage;
    private TextView greeting;
    private RecyclerView recyclerView;
    private View nav_host_fragment_activity_main2;
    //adapter
    private AdapterMain adapter;
    //lists
    private List<User> users;
    private List<ItemUserTeamCheck> items;
    //api interface
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        init();
        setSupportActionBar(mainToolbar);
        getSavedUserName();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        if(!isNetworkAvailable()){
            final Dialog dialog = new Dialog(MainActivity.this, R.style.ThemeDialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_internet_connection);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            MaterialButton btnExit = dialog.findViewById(R.id.btn_exit), btnSettings = dialog.findViewById(R.id.btn_settings);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveTaskToBack(true);
                    finish();
                    System.exit(0);
                    dialog.cancel();
                }
            });

            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });

            dialog.show();
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setUpRecyclerView();
        fillExampleList();

        setLanguage();

        if(SharedPrefManager.getInstance(MainActivity.this).getMode() == true){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void setLanguage(){
        String english = getResources().getString(R.string.english);
        String ukranian = getResources().getString(R.string.ukranian);
        String french =  getResources().getString(R.string.french);
        String russian = getResources().getString(R.string.russian);

        if(SharedPrefManager.getInstance(MainActivity.this).getLanguage().equals(english)){
            setLocal(MainActivity.this, "en");
        }else if(SharedPrefManager.getInstance(MainActivity.this).getLanguage().equals(ukranian)){
            setLocal(MainActivity.this, "ua");
        }else if(SharedPrefManager.getInstance(MainActivity.this).getLanguage().equals(french)){
            setLocal(MainActivity.this, "fr");
        }else if(SharedPrefManager.getInstance(MainActivity.this).getLanguage().equals(russian)){
            setLocal(MainActivity.this, "ru");
        }else{
            setLocal(MainActivity.this, "en");
        }
    }

    @Override
    public void setLanguage(AdapterView<?> parent, int position) {
        //Do nothing
    }

    @Override
    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void getSavedUserName(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> callName = apiInterface.getUser(SharedPrefManager.getInstance(MainActivity.this).getUser().getId());
        callName.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                greeting.setText("Hey, " + response.body().getName() + "!");
                String imageResponse = response.body().getImageProfile();
                Uri imageUri = Uri.parse(imageResponse);

                Glide.with(getApplicationContext())
                        .load(imageUri)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .apply(RequestOptions.noAnimation())
                        .thumbnail(0.3f)
                        .into(userImage);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void fillExampleList() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<User>> call = apiInterface.getUsersSearch(SharedPrefManager.getInstance(MainActivity.this).getUser().getId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                items = new ArrayList<>();
                for(User user : users){
                    items.add(new ItemUserTeamCheck(0, user, user.getName(), ""));
                }

                items.add(new ItemUserTeamCheck(1, new Check("Rizzoto"), "", "Rizzoto"));
                items.add(new ItemUserTeamCheck(1, new Check("Parmezza"), "", "Parmezza"));
                items.add(new ItemUserTeamCheck(1, new Check("The Byk"), "", "The Byk"));
                items.add(new ItemUserTeamCheck(1, new Check("Am!Bar."), "", "Am!Bar."));

                adapter = new AdapterMain(items, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void init(){
        mainToolbar = findViewById(R.id.main_toolbar);
        userImage = findViewById(R.id.image_user);
        recyclerView = findViewById(R.id.recycler_view_main);
        greeting = findViewById(R.id.text_view_greeting);
        nav_host_fragment_activity_main2 = findViewById(R.id.nav_host_fragment_activity_main2);
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    nav_host_fragment_activity_main2.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    nav_host_fragment_activity_main2.setVisibility(View.GONE);
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        recyclerView.setVisibility(View.GONE);
        nav_host_fragment_activity_main2.setVisibility(View.VISIBLE);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
        System.exit(0);
    }
}