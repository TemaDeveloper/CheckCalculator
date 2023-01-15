package com_calculator.java_calculator.checkcalculator.api;

import java.util.List;

import com_calculator.java_calculator.checkcalculator.models.Friend;
import com_calculator.java_calculator.checkcalculator.models.User;
import com_calculator.java_calculator.checkcalculator.models.UserList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("login_to.php")
    Call<UserList> getAllUser();

    @FormUrlEncoded
    @POST("addUser.php")
    Call<ResponseBody> signup(@Field("name") String name,
                              @Field("country") String country,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("about") String about,
                              @Field("image") String image);
    @GET("users.php")
    Call<List<User>> getUsers();

    @GET("usersSearching.php")
    Call<List<User>> getUsersSearch(@Query("id") Integer id);

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<ResponseBody> updateUser(@Field("id") Integer id,
                                  @Field("name") String name,
                                  @Field("country") String country,
                                  @Field("email") String email,
                                  @Field("about") String about,
                                  @Field("image") String image);
    @FormUrlEncoded
    @POST("updateFriend.php")
    Call<ResponseBody> updateFriend(@Field("id") int id,
                                    @Field("request") int request);

    @GET("getUser.php")
    Call<User> getUser(@Query("id") Integer id);

    @FormUrlEncoded
    @POST("addFriend.php")
    Call<ResponseBody> addFriend(@Field("name") String name,
                           @Field("country") String country,
                           @Field("about") String about,
                           @Field("image") String image,
                           @Field("my_id") int my_id,
                           @Field("user_id") int user_id,
                           @Field("request") int request,
                           @Field("friendName") String friendName,
                           @Field("friendImage") String friendImage);
    @FormUrlEncoded
    @POST("requests.php")
    Call<List<Friend>> getRequests(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("friends.php")
    Call<List<Friend>> getFriends(@Field("user_id") int user_id, @Field("my_id") int my_id);

    @GET("denyRequest.php")
    Call<List<Friend>> getDenyRequest(@Query("id") int id, @Query("request") int request);

}
