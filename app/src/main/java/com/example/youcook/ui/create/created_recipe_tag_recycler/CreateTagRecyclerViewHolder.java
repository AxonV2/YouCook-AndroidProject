package com.example.youcook.ui.create.created_recipe_tag_recycler;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.controller.SQLiteDataHelper;

import java.util.ArrayList;

public class CreateTagRecyclerViewHolder extends RecyclerView.ViewHolder
{
    //Final so it won't read from the database multiple times over
    private static final ArrayList<String> availableRecipeTags = new ArrayList<>(SQLiteDataHelper.GetAvailableTags());
    public static ArrayList<String> getAvailableRecipeTags(){ return availableRecipeTags; }

    private TextView TagDisplay;
    public TextView getTagDisplay(){ return TagDisplay; }

    private FrameLayout TagFrameLayout;

    //List for on click event below
    private static ArrayList<String> chosenTags = new ArrayList<>();
    public static ArrayList<String> getChosenTags(){ return chosenTags;}


    //HERE IS WHERE WE SET UP EVERYTHING RELATED TO DISPLAYING RECIPES.
    //VALUE ASSIGNMENT IS DONE INSIDE ADAPTER
    public CreateTagRecyclerViewHolder(@NonNull View itemView)
    {
        super(itemView);
        TagDisplay = itemView.findViewById(R.id.tagTextDisplay);
        TagFrameLayout = itemView.findViewById(R.id.tagFrameLayout);

        //On click listener for each tag
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String clickedTag = availableRecipeTags.get(getAdapterPosition());

                //Add to list and
                //change color to represent it being selected
                if (chosenTags.contains(clickedTag))
                {
                    chosenTags.remove(clickedTag);
                    TagFrameLayout.setBackgroundColor(Color.parseColor("#C6A300"));
                }
                else
                {
                    chosenTags.add(clickedTag);
                    TagFrameLayout.setBackgroundColor(Color.parseColor("#F6C500"));
                }
            }
        });
    }

}
