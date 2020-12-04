package com.example.youcook.ui.create.created_recipe_tag_recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;

public class CreateTagRecyclerAdapter extends RecyclerView.Adapter<CreateTagRecyclerViewHolder>
{
    public CreateTagRecyclerAdapter()
    {
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
    public CreateTagRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

            return new CreateTagRecyclerViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CreateTagRecyclerViewHolder holder, int position)
    {
        //Call variables held in HolderView and assign them.
        holder.getTagDisplay().setText(CreateTagRecyclerViewHolder.getAvailableRecipeTags().get(position));
    }

    @Override
    //This is what specifies how many times onBindViewHolder will run.
    public int getItemCount()
    {
        return CreateTagRecyclerViewHolder.getAvailableRecipeTags().size();
    }

}
