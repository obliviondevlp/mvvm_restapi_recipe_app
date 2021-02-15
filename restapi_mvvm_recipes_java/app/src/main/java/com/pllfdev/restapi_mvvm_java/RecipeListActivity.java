package com.pllfdev.restapi_mvvm_java;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pllfdev.restapi_mvvm_java.adapters.OnRecipeListener;
import com.pllfdev.restapi_mvvm_java.adapters.RecipeRecyclerAdapter;
import com.pllfdev.restapi_mvvm_java.models.Recipe;
import com.pllfdev.restapi_mvvm_java.utils.Testing;
import com.pllfdev.restapi_mvvm_java.utils.VerticalSpacingItemDecorator;
import com.pllfdev.restapi_mvvm_java.databinding.ActivityRecipeListBinding;
import com.pllfdev.restapi_mvvm_java.viewmodels.RecipeListViewModel;

import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "recyclerViewActivity";
    private ActivityRecipeListBinding binding;
    private final RecipeListViewModel mRecipeListViewModel = new RecipeListViewModel();
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!mRecipeListViewModel.isViewingRecipes()){
            //display search categories
            displaySearchCategories();
        }
        setSupportActionBar(binding.toobarSearch);
        //testRetrofitRequest();


    }

    private void searchRecipeApi(String query,int pageNumber){
        mRecipeListViewModel.searchRecipesApi(query,pageNumber);
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);

        //recyclerview separator
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        binding.recyclerView.addItemDecoration(itemDecorator);

        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!binding.recyclerView.canScrollVertically(1)){
                    //search the next page
                    mRecipeListViewModel.searchNextPage();
                }
            }
        });
    }

    public void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    if(mRecipeListViewModel.isViewingRecipes()){
                        Testing.printRecipes(recipes,"recipes test");
                        mRecipeListViewModel.setmIsPerformingQuery(false);
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
                mRecipeListViewModel.searchRecipesApi(query,1);
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
        mRecipeListViewModel.searchRecipesApi(category,1);
        binding.searchView.clearFocus();
    }

    private void displaySearchCategories(){
        mRecipeListViewModel.setmIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if(mRecipeListViewModel.onBackPressed()){
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