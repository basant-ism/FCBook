package com.example.fcbook.ui.gallery;

import android.app.Activity;
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
import com.example.fcbook.Models.User;
import com.example.fcbook.R;
import com.example.fcbook.response.MealResponse;
import com.example.fcbook.viewModels.FavoriteMealViewModels;
import com.example.fcbook.viewModels.MealViewModels;
import com.example.fcbook.viewModels.SearchedMealViewModels;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    FavoriteMealViewModels viewModels;
    public  static List<Meal> meals=new ArrayList<>();
    private  static MealAdapter adapter;
    private static Activity context;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        progressBar=root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView=root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter=new MealAdapter(getActivity(),meals);
        recyclerView.setAdapter(adapter);

        viewModels=new ViewModelProvider(this).get(FavoriteMealViewModels.class);
        context=getActivity();
        textView=root.findViewById(R.id.tv_exist);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            textView.setVisibility(View.GONE);
            db.collection("users").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        User user=documentSnapshot.toObject(User.class);
                        if(user!=null&&user.getMeals_id()!=null&&user.getMeals_id().size()>0)
                        {
                            getMeals(user.getMeals_id());
                        }
                        else
                        {
                            meals.clear();
                            textView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        meals.clear();
                        textView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    meals.clear();
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        else {
            meals.clear();
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void getMeals(List<String>mealsId)
    {

        meals.clear();
        for(String idMeal:mealsId)
        {

            viewModels.getFavoriteMeals(idMeal).observe(getActivity(), new Observer<MealResponse>() {
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
    }

}