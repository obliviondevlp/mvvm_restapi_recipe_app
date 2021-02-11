package com.pllfdev.restapi_mvvm_java.requests;


import com.pllfdev.restapi_mvvm_java.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static  RecipeApi recipeApi = retrofit.create(RecipeApi.class);
    public static RecipeApi getRecipeApi(){return recipeApi;}


}
