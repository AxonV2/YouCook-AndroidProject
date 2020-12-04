package com.example.youcook.ui.recipes.selected_recipe_recycler_handlers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.ui.recipes.selected_recipe_recycler_handlers.TagsRecyclerViewHolder;

public class ListTagsRecycleAdapter extends RecyclerView.Adapter<TagsRecyclerViewHolder>{

    private Integer passedInTagSize;
    private Integer passedInID;

    public ListTagsRecycleAdapter(int TagSize, int ID)
    {
        passedInTagSize = TagSize;
        passedInID = ID;
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
        //Passing in specific ID.
        return new TagsRecyclerViewHolder(view, passedInID);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TagsRecyclerViewHolder holder, int position)
    {
        //THIS IS WHERE WE WILL ASSIGN VALUES

        //Call variables held in HolderView and assign them.
        holder.getTagDisplay().setText(TagsRecyclerViewHolder.getRecipeTags().get(position));

    }

    @Override
    //This is what specifies how many times onBindViewHolder will run.
    public int getItemCount()
    {
        return passedInTagSize;
    }

}
