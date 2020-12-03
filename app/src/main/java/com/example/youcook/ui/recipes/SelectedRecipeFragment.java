package com.example.youcook.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.youcook.R;
import com.example.youcook.controller.SQLiteDataHelper;
import com.example.youcook.databinding.FragmentSelectedRecipeBinding;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.RecipeModel;
import com.example.youcook.ui.recipes.selected_recipe_recycler_handlers.TagsRecyclerViewAdapter;

import java.util.stream.Collectors;

public class SelectedRecipeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //LIVE DATA BINDINGS
        FragmentSelectedRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_recipe, container, false);

        binding.setViewModel(new RecipesViewModel());
        binding.setLifecycleOwner(this);

        //View used for id resources
        View returningView = binding.getRoot();

        //Getting recycler in selected recipe fragment
        RecyclerView recyclerView = returningView.findViewById(R.id.FullRecipeTagRecycler);

        //Add the following lines to create RecyclerView
        //Every item has fixed size?
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        //This time we set the recycler layout HORIZONTAL for tag display
        recyclerView.setLayoutManager(new LinearLayoutManager(returningView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        TagsRecyclerViewAdapter adapter = new TagsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        //Todo fix

        //Recipe Image
        ImageView recipeImage = returningView.findViewById(R.id.FullRecipeImage);

        Glide.with(returningView).load(RecipesViewModel.getRecipeImage())
                .override(1000,1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(recipeImage);


        //Author Image below recipe
        ImageView authorImage = returningView.findViewById(R.id.FullRecipeAuthorImage);

        Glide.with(returningView).load(RecipesViewModel.getAuthorImage())
                .override(250,250)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(authorImage);

        //endregion

        //Button to add to favorites.
        Button favBut = returningView.findViewById(R.id.FavoriteButton);

        //Favorite on click listener
        favBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //TODO CHANGE TO LOGGED IN USER
                String CurrentUserEmail = RecipesViewModel.getRecipeAuthorEmail().getValue();
                Integer CurrentRecipe = RecipesViewModel.getSelectedRecipeItemID().getValue();

                //Current recipe that we're checking
                IRecipeModel recipe = RecipeModel.Full_Recipe_List.stream().filter(x -> x.getRecipeID().equals(CurrentRecipe)).collect(Collectors.toList()).get(0);



                //0 = Added to favorites
                //1 = Removed from favorites
                Integer res = SQLiteDataHelper.FavoritesTableHandling(CurrentUserEmail, CurrentRecipe);

                if (res != 1)
                {
                    Toast.makeText(returningView.getContext(), "You have added " + RecipesViewModel.getRecipeTitle().getValue() + " to your favorites!", Toast.LENGTH_LONG).show();
                    IRecipeModel.Favorites_Recipe_List.add(recipe);
                }
                else{
                    Toast.makeText(returningView.getContext(), "You have removed " + RecipesViewModel.getRecipeTitle().getValue() + " from your favorites", Toast.LENGTH_LONG).show();
                    IRecipeModel.Favorites_Recipe_List.remove(recipe);
                }
                RecipesViewModel.UpdateFavoritesButton();
            }
        });


        //Observer for favorites button in case user clicks on it
        RecipesViewModel.getFavoritesButton().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                favBut.setText(s);
            }
        });


        return returningView;
    }
}
