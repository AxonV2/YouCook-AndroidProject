package com.example.youcook.ui.recipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youcook.R;
import com.example.youcook.context.MainActivity;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.RecipeModel;
import com.example.youcook.ui.create.CreateViewModel;
import com.example.youcook.ui.recipes.RecipesViewModel;
import com.example.youcook.ui.recipes.recipe_list_recycler_handlers.RecyclerViewRecipeAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class RecipesFavorites extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View returningView = inflater.inflate(R.layout.fragment_favorites, container, false);

        //USING VIEW TO GET RECYCLER IN SPECIFIED FRAGMENT
        RecyclerView recyclerView = returningView.findViewById(R.id.FavoriteViewer);

        // Add the following lines to create RecyclerView
        //Every item has fixed size?
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext()));


        //Send FAVORITES list through adapter
        RecyclerViewRecipeAdapter Adapter = new RecyclerViewRecipeAdapter(IRecipeModel.Favorites_Recipe_List);
        recyclerView.setAdapter(Adapter);


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
                Adapter.getFilter().filter(filter);
                return false;
            }
        });

        return returningView;

        /* OBSERVER
        recipesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });


         */

    }
}