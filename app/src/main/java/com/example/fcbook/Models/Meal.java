package com.example.fcbook.Models;

import com.google.gson.annotations.SerializedName;

public class Meal {
     @SerializedName("idMeal")
    private String idMeal;

     @SerializedName("strMeal")
    private String strMeal;

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("strInstructions")
    private String strInstructions;

    @SerializedName("strCategory")
    private String strCategory;

    @SerializedName("strArea")
    private String strArea;

    public String getIdMeal() {
        return idMeal;
    }


    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrMeal() {
        return strMeal;
    }



    public String getStrMealThumb() {
        return strMealThumb;
    }


}
