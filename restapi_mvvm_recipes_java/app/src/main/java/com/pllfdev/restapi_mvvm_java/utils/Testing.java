package com.pllfdev.restapi_mvvm_java.utils;

import android.util.Log;

import com.pllfdev.restapi_mvvm_java.models.Recipe;

import java.util.List;

public class Testing {
    public static void printRecipes(List<Recipe> recipes,String tag){
        for (Recipe recipe:recipes){
            Log.d(tag,"onChanged : " + recipe.getTitle());
        }
    }
}
