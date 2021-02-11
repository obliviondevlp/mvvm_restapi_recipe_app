package com.pllfdev.restapi_mvvm_java.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    private boolean mIsViewingRecipes;

    public RecipeListViewModel() {
        mIsViewingRecipes = false;
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query,int pageNumber){
        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipesApi(query,pageNumber);
    }

    public boolean ismIsViewingRecipes(){
        return  mIsViewingRecipes;
    }

    public void setmIsViewingRecipes(boolean isViewingRecipes){
        mIsViewingRecipes = isViewingRecipes;
    }



}
