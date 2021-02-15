package com.pllfdev.restapi_mvvm_java;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.pllfdev.restapi_mvvm_java.adapters.OnRecipeListener;
import com.pllfdev.restapi_mvvm_java.adapters.RecipeRecyclerAdapter;
import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeListBinding;
import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.utils.Testing;
import com.pllfdev.restapi_mvvm_java.utils.VerticalSpacingItemDecorator;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeListViewModel;
import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";
    private ActivityRecipeListBinding binding;
    private RecipeListViewModel mResipeListViewModel = new RecipeListViewModel();
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!mResipeListViewModel.isViewingRecipes()){
            //display search categories
            displaySearchCategories();
        }
        setSupportActionBar(binding.toobarSearch);
        //testRetrofitRequest();


    }

    private void searchRecipeApi(String query,int pageNumber){
        mResipeListViewModel.searchRecipesApi(query,pageNumber);
    }
    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);

        //recyclerview separator
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        binding.recipeList.addItemDecoration(itemDecorator);

        binding.recipeList.setAdapter(mAdapter);
        binding.recipeList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void subscribeObservers(){
        mResipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    if(mResipeListViewModel.isViewingRecipes()){
                        Testing.printRecipes(recipes,"recipes test");
                        mResipeListViewModel.setmIsPerformingQuery(false);
                        mAdapter.setRecipes(recipes);
                    }
                }
            }
        });
    }

/*    private void testRetrofitRequest(){
        mResipeListViewModel.searchRecipesApi("spinach",1);
    }*/

    private void initSearchView(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mAdapter.displayLoading();
                mResipeListViewModel.searchRecipesApi(query,1);
                binding.searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mResipeListViewModel.searchRecipesApi(category,1);
        binding.searchView.clearFocus();
    }

    private void displaySearchCategories(){
        mResipeListViewModel.setmIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if(mResipeListViewModel.onBackPressed()){
            super.onBackPressed();
        }else{
            displaySearchCategories();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_categories){
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}