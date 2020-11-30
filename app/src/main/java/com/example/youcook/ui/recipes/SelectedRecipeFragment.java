package com.example.youcook.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.ui.recipes.selected_recipe_recycler_handlers.TagsRecyclerViewAdapter;

public class SelectedRecipeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //View used for id resources
        View returningView = inflater.inflate(R.layout.fragment_selected_recipe, container, false);

        //Getting recycler in selected recipe fragment
        RecyclerView recyclerView = returningView.findViewById(R.id.FullRecipeTagRecycler);

        // Add the following lines to create RecyclerView
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        //This time we set the recycler layout HORIZONTAL for tag display
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        TagsRecyclerViewAdapter adapter = new TagsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);



        final TextView RecipeTitle = returningView.findViewById(R.id.FullRecipeTitleText);
        final TextView RecipeLongDescription = returningView.findViewById(R.id.FullRecipeLongDescription);
        final TextView RecipeQuickDescription = returningView.findViewById(R.id.FullRecipeQuickDesc);
        final TextView RecipePrep = returningView.findViewById(R.id.FullRecipePrepText);
        final TextView RecipeCook = returningView.findViewById(R.id.FullRecipeCookText);
        final TextView RecipeDone = returningView.findViewById(R.id.FullRecipeDoneText);
        final TextView AuthorName = returningView.findViewById(R.id.FullRecipeAuthorName);
        //Need to implement
        final ImageView AuthorImage = returningView.findViewById(R.id.FullRecipeAuthorImage);
        final ImageView RecipeImage = returningView.findViewById(R.id.FullRecipeImage);

        //Region Observers
        RecipesViewModel.getRecipeTitle().observe(getViewLifecycleOwner(),  Value -> { RecipeTitle.setText(Value); });
        RecipesViewModel.getRecipeLongDescription().observe(getViewLifecycleOwner(), Value -> { RecipeLongDescription.setText(Value); });
        RecipesViewModel.getRecipeQuickDescription().observe(getViewLifecycleOwner(), Value -> { RecipeQuickDescription.setText(Value); });
        RecipesViewModel.getRecipePrep().observe(getViewLifecycleOwner(), Value -> { RecipePrep.setText("Prep " + Value.toString() + " Min(s)"); });
        RecipesViewModel.getRecipeCook().observe(getViewLifecycleOwner(), Value -> { RecipeCook.setText("Cook " + Value.toString() + " Min(s)"); });
        RecipesViewModel.getRecipeDone().observe(getViewLifecycleOwner(), Value -> { RecipeDone.setText("Done " + Value.toString() + " Min(s)"); });
        RecipesViewModel.getRecipeAuthorName().observe(getViewLifecycleOwner(), Value -> { AuthorName.setText(Value); });


        //RecipesViewModel.FUCK.observe(getViewLifecycleOwner(), Image -> { RecipeImage.setText(Image); });
        //RecipesViewModel.FUCK.observe(getViewLifecycleOwner(), Image -> { AuthorImage.setText(Image); });

        //endregion

        return returningView;
    }
}
