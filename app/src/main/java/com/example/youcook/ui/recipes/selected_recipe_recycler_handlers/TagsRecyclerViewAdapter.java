package com.example.youcook.ui.recipes.selected_recipe_recycler_handlers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.ui.recipes.RecipesViewModel;

public class TagsRecyclerViewAdapter extends RecyclerView.Adapter<TagsRecyclerViewHolder>{

    public TagsRecyclerViewAdapter() {
    }

    //Provide custom layout file
    @Override
    public int getItemViewType(final int position)
    {
        //THE CREATED ITEM LAYOUT XML
        return R.layout.recipe_item_recycler_layout;
    }

    @NonNull
    @Override
    public TagsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        //Need ViewModel for selected RecipeID
        //Calling Static method from recipesViewModel
        TagsRecyclerViewHolder viewHolder = new TagsRecyclerViewHolder(view, RecipesViewModel.getSelectedRecipeItemID().getValue());
        return viewHolder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TagsRecyclerViewHolder holder, int position)
    {
        //THIS IS WHERE WE WILL ASSIGN VALUES

        //Call variables held in HolderView and assign them.
        //We use the filtered list in case any filters are applied


        holder.getTagDisplay().setText(TagsRecyclerViewHolder.getRecipeTags().get(position));

    }

    @Override
    public int getItemCount()
    {
        return RecipesViewModel.getListSize();
    }

}
