package com.pllfdev.restapi_mvvm_java.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.repositories.RecipeRepository;

public class RecipeDetailsViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mDidRetriveRecipe;

    public RecipeDetailsViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetriveRecipe = false;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimeout(){
        return mRecipeRepository.isRecipeRequestTimeout();
    }

    public void searchRecipeById(String recipeId){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRetrievedRecipe(boolean retrievedRecipe){
        mDidRetriveRecipe = retrievedRecipe;
    }

    public boolean didRetrievedRecipe(){
        return mDidRetriveRecipe;
    }
}
