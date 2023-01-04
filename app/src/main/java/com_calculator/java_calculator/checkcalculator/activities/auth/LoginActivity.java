package com_calculator.java_calculator.checkcalculator.activities.auth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.activities.MainActivity;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.User;
import com_calculator.java_calculator.checkcalculator.models.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton buttonLogIn;
    private TextView textSignUp, forgetPassword;
    private TextInputEditText password, email;
    private int id;
    private String email_holder, password_holder/*, name_holder, country_holder, about_holder*/;
    private ApiInterface apiInterface;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        forgetPassword.setOnClickListener(this);
        textSignUp.setOnClickListener(this);
        buttonLogIn.setOnClickListener(this);

        if(!isNetworkAvailable()){
            final Dialog dialog = new Dialog(LoginActivity.this, R.style.ThemeDialog);
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

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void init() {
        userList = new ArrayList<>();
        buttonLogIn = findViewById(R.id.button_log_in);
        password = findViewById(R.id.edit_text_password);
        email = findViewById(R.id.edit_text_email);
        textSignUp = findViewById(R.id.text_view_sign_up);
        forgetPassword = findViewById(R.id.text_view_forget_password);
    }

    private void login() {
        password_holder = String.valueOf(password.getText()).trim();
        email_holder = String.valueOf(email.getText()).trim();

        if (password_holder.isEmpty() || email_holder.isEmpty()) {
            Toast.makeText(this, "All of the fields must to be filled", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<UserList> call = apiInterface.getAllUser();
            call.enqueue(new Callback<UserList>() {
                @Override
                public void onResponse(Call<UserList> call, Response<UserList> response) {
                    if (response.isSuccessful()) {

                        userList = response.body().getResult();

                        Boolean login = false;
                        for (int i = 0; i < userList.size(); i++) {
                            if (email_holder.equals(userList.get(i).getEmail())
                                    && password_holder.equals(userList.get(i).getPassword())) {

                                id = userList.get(i).getId();
                               /* name_holder = userList.get(i).getName();
                                country_holder = userList.get(i).getCountry();
                                about_holder = userList.get(i).getAbout();*/

                                login = true;

                            }
                        }
                        if (login) {

                            User user = new User(id);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            SharedPrefManager.getInstance(LoginActivity.this).setMode(false);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Email Id of Password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserList> call, Throwable t) {
                    Log.d("TAG_RES", t.getMessage() + " " + t.getCause());
                }
            });

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_log_in:
                login();
                break;
            case R.id.text_view_sign_up:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.text_view_forget_password:
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                break;
        }
    }


}