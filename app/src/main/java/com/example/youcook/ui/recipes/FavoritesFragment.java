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


public class FavoritesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View returningView = inflater.inflate(R.layout.fragment_favorites, container, false);

        //Using view to get recycler id from fragment
        RecyclerView recyclerView = returningView.findViewById(R.id.FavoriteViewer);

        //Following lines create RecyclerView
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
            //Filter on submit = nothing
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
    }
}