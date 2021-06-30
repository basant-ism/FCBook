package com.example.fcbook.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fcbook.Models.Meal;
import com.example.fcbook.Models.User;
import com.example.fcbook.R;
import com.example.fcbook.dialogs.AuthDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.FoodViewHolder> {
    Context context;
    List<Meal>meals;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    public static List<String> meals_id=new ArrayList<>();

    public MealAdapter(Context context, List<Meal>meals)
    {
        this.context=context;
        this.meals=meals;
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        getMealsIds();

    }//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
// git pull https://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@github.com/BASANTKUMARSAINI/FCBook.git
    private void getMealsIds() {
        if(mAuth.getCurrentUser()!=null)
        {
            Log.d("TAG", "getMealsIds: ");
            db.collection("users").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user.getMeals_id() != null)
                            meals_id = user.getMeals_id();

                    }
                }
            });
        }
        else{
            meals_id=new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_layout,parent,false);

        return new FoodViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
         holder.mealName.setText(meals.get(position).getStrMeal());
         holder.mealArea.setText(meals.get(position).getStrArea());
         holder.mealCategory.setText(meals.get(position).getStrCategory());
         holder.mealInstructions.setText(meals.get(position).getStrInstructions());
         if(meals.get(position).getStrMealThumb()!=null)
         {
             Picasso.get()
                     .load(meals.get(position).getStrMealThumb())
                     .into(holder.mealImage);

         }
         holder.favMeal.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
         holder.setFabButton(meals.get(position).getIdMeal());
         holder.favMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null) {
                    if (meals_id.contains(meals.get(position).getIdMeal())) {
                        holder.favMeal.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_border));
                        meals_id.remove(meals.get(position).getIdMeal());
                    } else {
                        holder.favMeal.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark));
                        meals_id.add(meals.get(position).getIdMeal());
                    }


                    updateData();
                }
                else{
                    showDialog(meals.get(position).getIdMeal());
                }
            }
        });


    }

    private void showDialog(String idMeal) {
        AuthDialog dialog=new AuthDialog(context,idMeal);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void updateData() {
        HashMap<String ,Object>hashMap=new HashMap<>();
        hashMap.put("meals_id",meals_id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.collection("users").document(mAuth.getUid()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        return;
                    }
                });
                return;
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView mealImage,favMeal;
        public TextView mealName,mealCategory,mealArea;
        public ExpandableTextView mealInstructions;
        Context context;

        public FoodViewHolder(Context context,@NonNull View itemView) {
            super(itemView);
            this.context=context;
            mealImage=itemView.findViewById(R.id.img_meal);
            mealName=itemView.findViewById(R.id.tv_meal_name);
            mealInstructions=itemView.findViewById(R.id.expand_text_view);
            favMeal=itemView.findViewById(R.id.img_fav_meal);
            mealCategory=itemView.findViewById(R.id.tv_meal_category);
            mealArea=itemView.findViewById(R.id.tv_meal_area);

        }

        public void setFabButton(String idMeal) {
            if(meals_id!=null)
            {

                if(meals_id.contains(idMeal))
                {
                    favMeal.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark));
                }
                else{
                    favMeal.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_border));
                }
            }
        }
    }
}
