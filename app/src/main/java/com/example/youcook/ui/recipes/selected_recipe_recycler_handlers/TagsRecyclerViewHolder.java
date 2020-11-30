package com.example.youcook.ui.recipes.selected_recipe_recycler_handlers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.models.IRecipeModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TagsRecyclerViewHolder extends RecyclerView.ViewHolder
{

    private static ArrayList<String> RecipeTags;
    private TextView TagDisplay;

    //HERE IS WHERE WE SET UP EVERYTHING RELATED TO DISPLAYING RECIPES.
    //VALUE ASSIGNMENT IS DONE INSIDE ADAPTER
    public TagsRecyclerViewHolder(@NonNull View itemView, Integer RecipeID)
    {
        super(itemView);
        TagDisplay = itemView.findViewById(R.id.tagTextDisplay);

        //ID Passed in so we can retrieve tags
        RecipeTags = new ArrayList<String>()
        {{
            //Can't tell if this is good or bad
            //Lambda used to get instance of passed in ID;
            IRecipeModel InstanceFromID = IRecipeModel.Full_Recipe_List.stream().filter(x -> x.getRecipeID().equals(RecipeID)).collect(Collectors.toList()).get(0);
            //Add all tags from given recipe
            addAll(InstanceFromID.getTags());
        }};
    }

    public static ArrayList<String> getRecipeTags(){
        return RecipeTags;
    }
    public TextView getTagDisplay(){
        return TagDisplay;
    }
}