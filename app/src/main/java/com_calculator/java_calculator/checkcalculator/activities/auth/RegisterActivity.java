package com_calculator.java_calculator.checkcalculator.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.activities.MainActivity;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import com_calculator.java_calculator.checkcalculator.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton registerButton;
    private ImageView back;
    private TextInputEditText name, email, country, password;
    private String name_holder, email_holder, country_holder, password_holder;
    private ApiInterface apiInterfaceUsers, apiInterfaceRegister;
    private int id;
    public static final String INITIAL_ABOUT_TEXT = "Share something about you...";
    public static final String INITIAL_IMAGE_TEXT = "http://192.168.1.65/loginRegister/images/male.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back = findViewById(R.id.image_view_back);
        registerButton = findViewById(R.id.button_sign_up);
        name = findViewById(R.id.edit_text_name);
        email = findViewById(R.id.edit_text_email);
        country = findViewById(R.id.edit_text_country);
        password = findViewById(R.id.edit_text_password);

        back.setOnClickListener(view -> onBackPressed());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        name_holder = String.valueOf(name.getText()).trim();
        country_holder = String.valueOf(country.getText()).trim();
        email_holder = String.valueOf(email.getText()).trim();
        password_holder = String.valueOf(password.getText()).trim();

        if ((name_holder.isEmpty()) ||
                (country_holder.isEmpty()) ||
                (email_holder.isEmpty()) ||
                (password_holder.isEmpty())) {
            Toast.makeText(this, "All of the fields must to be filled", Toast.LENGTH_SHORT).show();
        } else {
            getAllUsers();
        }

    }

    private void getAllUsers(){
        apiInterfaceUsers = ApiClient.getApiClient().create(ApiInterface.class);
        id = 0;
        Call<List<User>> call = apiInterfaceUsers.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                for(User u : users){
                    int idGetter = u.getId();
                    if(idGetter > id){
                        id = idGetter;
                    }
                }
                apiInterfaceRegister = ApiClient.getApiClient().create(ApiInterface.class);
                Call<ResponseBody> registerCall = apiInterfaceRegister.signup(name_holder, country_holder, password_holder, email_holder, INITIAL_ABOUT_TEXT, INITIAL_IMAGE_TEXT);
                registerCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        id = id + 1;
                        User user = new User(id);
                        SharedPrefManager.getInstance(RegisterActivity.this).userLogin(user);
                        SharedPrefManager.getInstance(RegisterActivity.this).setMode(false);
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

}