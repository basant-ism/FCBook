package com.example.fcbook.networks;

import com.example.fcbook.response.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("s")
    Call<MealResponse> getAllMeals(@Query("s") String mealName);
    @GET("i")
    Call<MealResponse> getFavoriteMeals(@Query("i") String idMeals);

}
