package com.example.fcbook.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fcbook.repositories.FavoriteMealRepository;

import com.example.fcbook.response.MealResponse;

public class FavoriteMealViewModels extends ViewModel {
    private FavoriteMealRepository favoriteMealRepository;
    public FavoriteMealViewModels()
    {
        favoriteMealRepository=new FavoriteMealRepository();
    }
    public LiveData<MealResponse> getFavoriteMeals(String name)
    {
        return favoriteMealRepository.getFavoriteMeals(name);

    }
}
