package com.example.fcbook.networks;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit()
    {

        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/search.php/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
