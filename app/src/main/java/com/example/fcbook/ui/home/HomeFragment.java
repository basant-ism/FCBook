package com.example.fcbook.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fcbook.Adapters.MealAdapter;
import com.example.fcbook.Models.Meal;
import com.example.fcbook.R;
import com.example.fcbook.response.MealResponse;
import com.example.fcbook.viewModels.MealViewModels;
import com.example.fcbook.viewModels.SearchedMealViewModels;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    MealViewModels viewModels;
    private static SearchedMealViewModels searchedMealViewModels;
    public  static List<Meal> meals=new ArrayList<>();
    private  static MealAdapter adapter;
    private static Activity context;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar=root.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        recyclerView=root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter=new MealAdapter(getActivity(),meals);
        recyclerView.setAdapter(adapter);

        viewModels=new ViewModelProvider(this).get(MealViewModels.class);
        searchedMealViewModels=new ViewModelProvider(this).get(SearchedMealViewModels.class);
        context=getActivity();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMeals();
    }

    private void getMeals()
    {

        meals.clear();
        viewModels.getMeals().observe(getActivity(), new Observer<MealResponse>() {
            @Override
            public void onChanged(MealResponse mealResponse) {
                if(mealResponse!=null)
                {
                    if(mealResponse.getMeals()!=null)
                    {
                        meals.addAll(mealResponse.getMeals());
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
    public  static void getSearchedMeal(String mealName) {

        searchedMealViewModels.getSearchedMeals(mealName).observe((LifecycleOwner) context, new Observer<MealResponse>() {
            @Override
            public void onChanged(MealResponse mealResponse) {
                if(mealResponse!=null)
                {
                    if(mealResponse.getMeals()!=null)
                    {
                        meals.clear();
                        meals.addAll(mealResponse.getMeals());
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}