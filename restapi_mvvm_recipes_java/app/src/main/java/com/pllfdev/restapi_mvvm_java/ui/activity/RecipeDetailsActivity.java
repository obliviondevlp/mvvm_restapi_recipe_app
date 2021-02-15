package com.pllfdev.restapi_mvvm_java.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeDetailsBinding;
import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeListBinding;
import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeDetailsViewModel;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeListViewModel;

public class RecipeDetailsActivity extends BaseActivity {

    private ActivityRecipeDetailsBinding binding;
    private RecipeDetailsViewModel mRecipeDetailsViewModel = new RecipeDetailsViewModel();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIncomingIntent();
        subscribeObservers();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d("teste","getIncomingIntent " + recipe.getTitle());
            Log.d("teste","getIncomingIntent " + recipe.getRecipe_id());

            mRecipeDetailsViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void subscribeObservers(){
        mRecipeDetailsViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if(recipe != null){
                    Log.d("TESTE", "onChanged: ------------------------------------------ ");
                    Log.d("TESTE", "onChanged: " + recipe.getTitle());
                    for(String ingredient : recipe.getIngredients()){
                        Log.d("TESTE", "onChanged: " + ingredient);
                    }
                }
            }
        });
    }
}
