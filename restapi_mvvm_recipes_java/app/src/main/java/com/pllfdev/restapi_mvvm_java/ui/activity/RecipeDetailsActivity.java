package com.pllfdev.restapi_mvvm_java.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pllfdev.restapi_mvvm_java.R;
import com.pllfdev.restapi_mvvm_java.adapters.RecipeViewHolder;
import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeDetailsBinding;
import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeListBinding;
import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeDetailsViewModel;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeListViewModel;

import java.text.DecimalFormat;

public class RecipeDetailsActivity extends BaseActivity {

    private ActivityRecipeDetailsBinding binding;
    private RecipeDetailsViewModel mRecipeDetailsViewModel = new RecipeDetailsViewModel();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showProgressBar(true);
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
                    if(recipe.getRecipe_id().equals(mRecipeDetailsViewModel.getRecipeId())){
                        setProprieties(recipe);
                    }
                }
            }
        });
    }

    private void setProprieties(Recipe recipe){
        if(recipe != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this).setDefaultRequestOptions(requestOptions).load(recipe.getImage_url()).into(binding.recipeImage);
            binding.recipeTitle.setText(recipe.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            binding.recipeSocialScore.setText(String.valueOf(decimalFormat.format(recipe.getSocial_rank())));
            binding.ingredientsContainer.removeAllViews();
            for(String ingredient : recipe.getIngredients()){
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                binding.ingredientsContainer.addView(textView);
            }
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        binding.scrollviewParent.setVisibility(View.VISIBLE);
    }
}
