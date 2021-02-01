package com.example.fcbook.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fcbook.repositories.MealRepository;
import com.example.fcbook.response.MealResponse;

public class MealViewModels extends ViewModel {
    private MealRepository mealRepository;
    public MealViewModels()
    {
        mealRepository=new MealRepository();
    }
    public LiveData<MealResponse>getMeals()
    {
        return mealRepository.getMeals();

    }

}
