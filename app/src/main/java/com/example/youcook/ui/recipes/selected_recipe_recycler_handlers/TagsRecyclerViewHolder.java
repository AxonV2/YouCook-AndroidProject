package com.example.youcook.ui.recipes.selected_recipe_recycler_handlers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.RecipeModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TagsRecyclerViewHolder extends RecyclerView.ViewHolder
{
    private static ArrayList<String> RecipeTags;
    public static ArrayList<String> getRecipeTags(){
        return RecipeTags;
    }

    private TextView TagDisplay;
    public TextView getTagDisplay(){
        return TagDisplay;
    }

    //HERE IS WHERE WE SET UP EVERYTHING RELATED TO DISPLAYING RECIPES.
    //VALUE ASSIGNMENT IS DONE INSIDE ADAPTER
    public TagsRecyclerViewHolder(@NonNull View itemView, Integer RecipeID)
    {
        super(itemView);
        TagDisplay = itemView.findViewById(R.id.tagTextDisplay);

        //Lambda used to get instance of passed in ID;
        //Note: Raz - Can't tell if this is good or bad
        IRecipeModel InstanceFromID = RecipeModel.Full_Recipe_List.stream().filter(x -> x.getRecipeID().equals(RecipeID)).collect(Collectors.toList()).get(0);

        RecipeTags = new ArrayList<String>()
        {{
            //Add all tags from given recipe
            addAll(InstanceFromID.getTags());
        }};
    }



}