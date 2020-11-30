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
import com.example.youcook.ui.recipes.recipe_list_recycler_handlers.RecyclerViewRecipeAdapter;
import com.example.youcook.views.MainActivity;

public class RecipesFragment extends Fragment {

    private RecyclerViewRecipeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //USE THIS VIEW TO GET ITEM ID'S
        View returningView = inflater.inflate(R.layout.fragment_recipes, container, false);

        //USING VIEW TO GET RECYCLER IN SPECIFIED FRAGMENT
        RecyclerView recyclerView = returningView.findViewById(R.id.RecipeViewer);

        // Add the following lines to create RecyclerView
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext()));
        adapter = new RecyclerViewRecipeAdapter();
        recyclerView.setAdapter(adapter);

        //STATIC VALUE ASSIGNED IN MAIN ACTIVITY
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
                adapter.getFilter().filter(filter);
                return false;
            }
        });

        //Observer for removed text in fragmentRecipes
        /*
        RecipesViewModel recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        final TextView textView = returningView.findViewById(R.id.text_recipes);

        recipesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });
         */

        return returningView;
    }
}