package com.example.fcbook.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fcbook.repositories.MealRepository;
import com.example.fcbook.repositories.SearchMealRepository;
import com.example.fcbook.response.MealResponse;

public class SearchedMealViewModels extends ViewModel {
    private SearchMealRepository searchMealRepository;
    public SearchedMealViewModels()
    {
        searchMealRepository=new SearchMealRepository();
    }
    public LiveData<MealResponse>getSearchedMeals(String name)
    {
        return searchMealRepository.getSearchedMeals(name);

    }

}
