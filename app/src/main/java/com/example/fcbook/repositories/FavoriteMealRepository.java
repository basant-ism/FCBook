package com.example.fcbook.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fcbook.networks.ApiClient;
import com.example.fcbook.networks.ApiClient2;
import com.example.fcbook.networks.ApiService;
import com.example.fcbook.response.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMealRepository {
    private ApiService apiService;
    public FavoriteMealRepository()
    {
        apiService= ApiClient2.getRetrofit().create(ApiService.class);
    }
    public LiveData<MealResponse> getFavoriteMeals(String idMeal)
    {
        MutableLiveData<MealResponse> data=new MutableLiveData<>();
        apiService.getFavoriteMeals(idMeal).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }
}
