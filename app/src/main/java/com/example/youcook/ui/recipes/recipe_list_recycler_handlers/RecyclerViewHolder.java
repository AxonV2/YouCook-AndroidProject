package com.example.youcook.ui.recipes.recipe_list_recycler_handlers;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.ui.recipes.RecipesViewModel;

public class RecyclerViewHolder extends RecyclerView.ViewHolder
{
    //One ViewHolder contains
    // necessary UI information about one item in RecyclerView.
    // In this example, itemView is the ConstraintLayout.

    private final TextView Title, QuickDescription, RecipePrep, RecipeCook, RecipeDone;
    private final ImageView RecipeImage;

    public TextView getTitle(){
        return Title;
    }
    public TextView getQuickDescription(){
        return QuickDescription;
    }
    public TextView getRecipePrep(){
        return RecipePrep;
    }
    public TextView getRecipeCook(){
        return RecipeCook;
    }
    public TextView getRecipeDone(){
        return RecipeDone;
    }
    public ImageView getRecipeImage(){
        return RecipeImage;
    }

    //HERE IS WHERE WE SET UP EVERYTHING RELATED TO DISPLAYING RECIPES.
    //VALUE ASSIGNMENT IS DONE INSIDE ADAPTER
    public RecyclerViewHolder(@NonNull View itemView)
    {
        super(itemView);
        Title = itemView.findViewById(R.id.recipesListTitleText);
        QuickDescription = itemView.findViewById(R.id.recipesListQuickDesc);
        RecipePrep = itemView.findViewById(R.id.recipesListPrepText);
        RecipeCook = itemView.findViewById(R.id.recipesListCookText);
        RecipeDone = itemView.findViewById(R.id.recipesListDoneText);
        RecipeImage = itemView.findViewById(R.id.recipesListImage);

        //This is returning the array position of clicked recipe.
        //If list is filtered array is also filtered, but all we need is to make list public
        //And then from that position get recipe ID and work off of that?
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Testing
                //Log.d("Tag", "onClick：" + getAdapterPosition() + " " + RecyclerViewRecipeAdapter.GetFilteredList().get(getAdapterPosition()).getRecipeQuickDescription());

                //From filtered list get current position's ID value.
                Integer RecipeID = RecyclerViewRecipeAdapter.GetFilteredList().get(getAdapterPosition()).getRecipeID();
                //Save it's data to the view model
                RecipesViewModel.setSelectedItem(RecipeID);

                //Trying to navigate controllers not fragments
                //The (v) passed in findNavController is the View from overwritten onClick
                //Custom navigation anim made in navigation -> mobile_navigation
                // note - hike; Don't like this but it works
                try {
                    Navigation.findNavController(v).navigate(R.id.action_nav_to_selected_recipe);
                }
                catch(Exception e) {
                    Navigation.findNavController(v).navigate(R.id.action_nav_favorites_to_nav_selected_recipe);
                }
            }
        });
    }
}