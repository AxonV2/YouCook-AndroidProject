package com.example.youcook.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.context.MainActivity;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.ui.recipes.recipe_list_recycler_handlers.RecyclerViewRecipeAdapter;

public class RecipesFragment extends Fragment {

    private RecyclerViewRecipeAdapter FullRecipeListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //USE THIS VIEW TO GET ITEM ID'S
        View returningView = inflater.inflate(R.layout.fragment_recipes, container, false);

        //USING VIEW TO GET RECYCLER IN SPECIFIED FRAGMENT
        RecyclerView recyclerView = returningView.findViewById(R.id.RecipeViewer);

        // Add the following lines to create RecyclerView
        //Every item has fixed size?
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext()));
        FullRecipeListAdapter = new RecyclerViewRecipeAdapter(IRecipeModel.Full_Recipe_List);
        recyclerView.setAdapter(FullRecipeListAdapter);

        //Static menu item set in MainActivity
        SearchView searchView = (SearchView) MainActivity.searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            //Filter on submit
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            //Real-time filtering.
            @Override
            public boolean onQueryTextChange(String filter)
            {
                FullRecipeListAdapter.getFilter().filter(filter);
                return false;
            }
        });

        return returningView;
    }
}