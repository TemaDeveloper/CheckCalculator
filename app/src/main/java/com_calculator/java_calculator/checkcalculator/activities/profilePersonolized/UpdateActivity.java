package com_calculator.java_calculator.checkcalculator.activities.profilePersonolized;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.api.ApiClient;
import com_calculator.java_calculator.checkcalculator.api.ApiInterface;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 777;
    private Uri path;
    private TextInputEditText name, email, country, about;
    private String name_holder, email_holder, country_holder, about_holder, image_holder;
    private ExtendedFloatingActionButton updateButton;
    private int id;
    private ImageView userPhotoUpdated, alphaBlue;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        }

        init();
        setFieldUpdateText();

        findViewById(R.id.image_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        alphaBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                userPhotoUpdated.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public String convertImageToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void setFieldUpdateText() {
        name_holder = getIntent().getStringExtra("name");
        email_holder = getIntent().getStringExtra("email");
        country_holder = getIntent().getStringExtra("country");
        about_holder = getIntent().getStringExtra("about");
        image_holder = getIntent().getStringExtra("image");
        Uri imageUri = Uri.parse(image_holder);

        Glide.with(UpdateActivity.this)
                .load(imageUri)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.noAnimation())
                .thumbnail(0.3f)
                .into(userPhotoUpdated);

        name.setText(name_holder);
        email.setText(email_holder);
        country.setText(country_holder);
        about.setText(about_holder);
    }

    private void init() {
        name = findViewById(R.id.edit_text_name);
        email = findViewById(R.id.edit_text_email);
        country = findViewById(R.id.edit_text_country);
        about = findViewById(R.id.edit_text_about);
        updateButton = findViewById(R.id.button_update);
        userPhotoUpdated = findViewById(R.id.image_view_user_photo_updated);
        alphaBlue = findViewById(R.id.image_view_alpha_blue);
    }


    private void update() {
        name_holder = name.getText().toString().trim();
        email_holder = email.getText().toString().trim();
        country_holder = country.getText().toString().trim();
        about_holder = about.getText().toString().trim();
        id = SharedPrefManager.getInstance(UpdateActivity.this).getUser().getId();
        String image;
        //if(bitmap == null){
        //    image = "";
        //}else{
            image = convertImageToString(bitmap);
        //}

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.updateUser(
                id,
                name_holder,
                country_holder,
                email_holder,
                about_holder,
                image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(UpdateActivity.this,
                        "Response" + response.body().toString() + " " + response.message(),
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(UpdateActivity.this, ProfileActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}