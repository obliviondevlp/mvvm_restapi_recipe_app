package com.pllfdev.restapi_mvvm_java.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();
    private int aux = 0;

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }else{
                    //search dataabase cache
                    doneQuery(null);
                }
            }
        });
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    private void doneQuery(List<Recipe> list){
        if(list != null && list.size() > 0){
            if (list.size() % 30 != 0) {
                if(aux != list.size()){
                    aux = list.size();
                    mIsQueryExhausted.setValue(true);
                }
            }
        }
        else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeApiClient.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimeout(){
        return mRecipeApiClient.isRecipeRequestTimeout();
    }


    public void searchRecipeById(String recipeId){
        mRecipeApiClient.searchRecipeById(recipeId);
    }

    public void searchRecipesApi(String query,int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mRecipeApiClient.searchRecipesApi(query,pageNumber);
    }

    public void searchNextPage(){
        searchRecipesApi(mQuery,mPageNumber + 1);
    }

    public void cancelRequest(){
        mRecipeApiClient.cancelRequest();
    }

}
