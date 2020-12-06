package com.example.youcook.ui.recipes.recipe_list_recycler_handlers;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.youcook.R;
import com.example.youcook.context.BitmapHandler;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.ui.recipes.selected_recipe_recycler_handlers.ListTagsRecycleAdapter;

import java.util.ArrayList;

public class RecyclerViewRecipeAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements Filterable
{
    //Original list coming from IRecipeModel
    private ArrayList<IRecipeModel> Recipes_Original;

    //Remember you do not want a reference to the list you want a fully new list.
    //This one will be filtered so items will be removed.
    static private ArrayList<IRecipeModel> Recipes_Filtered;

    static public ArrayList<IRecipeModel> GetFilteredList(){ return Recipes_Filtered; }


    public RecyclerViewRecipeAdapter(ArrayList<IRecipeModel> List)
    {
        Recipes_Original = new ArrayList<>(List);
        Recipes_Filtered = new ArrayList<>(List);
    }


    //Provide custom layout file
    @Override
    public int getItemViewType(final int position)
    {
        //THE CREATED ITEM LAYOUT XML
        return R.layout.recipe_list_recycler_items_layout;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Give view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        //THIS IS WHERE WE WILL ASSIGN VALUES

        //Call variables held in HolderView and assign them.
        //We use the filtered list in case any filters are applied
        holder.getTitle().setText(Recipes_Filtered.get(position).getRecipeTitle());
        holder.getQuickDescription().setText(Recipes_Filtered.get(position).getRecipeQuickDescription());
        holder.getRecipePrep().setText("Prep " + Recipes_Filtered.get(position).getPrepTime() + " Min(s)");
        holder.getRecipeCook().setText("Cook " + Recipes_Filtered.get(position).getCookTime() + " Min(s)");
        holder.getRecipeDone().setText("Prep " + Recipes_Filtered.get(position).getDoneTime() + " Min(s)");

        //Using current position against filtered list to get it's image URL

        if (!URLUtil.isValidUrl(Recipes_Filtered.get(position).getRecipeImageURL()))
        {
            Bitmap image = BitmapHandler.StringToBitMap(Recipes_Filtered.get(position).getRecipeImageURL());
            //Log.d("Tag", "Testing: " + image + Recipes_Filtered.get(position).getRecipeImageURL());
            Glide.with(holder.itemView).load(image)
                    .apply(new RequestOptions().override(1000, 600))
                    .centerCrop()
                    .into(holder.getRecipeImage());
        }
        else
        {
            Glide.with(holder.itemView).load(Recipes_Filtered.get(position).getRecipeImageURL())
                    .apply(new RequestOptions().override(1000, 600))
                    .centerCrop()
                    .into(holder.getRecipeImage());
        }

        //Recycler variables for Tag Display
        RecyclerView recyclerView = holder.itemView.findViewById(R.id.recipesListTagRecycler);

        //Add the following lines to create RecyclerView
        //Every item has fixed size?
        //Used for optimization purposes, will probably have to be set to false.
        recyclerView.setHasFixedSize(true);

        //Layout and Adapter
        //This time we set the recycler layout HORIZONTAL for tag display
        recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Pass in tag size for item count and pass in ID for ViewHolder
        ListTagsRecycleAdapter adapter = new ListTagsRecycleAdapter(Recipes_Filtered.get(position).getTags().size(), Recipes_Filtered.get(position).getRecipeID());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount()
    {
        //Will be set to FILTERED list size.
        //This is what specifies how many times onBindViewHolder will run.
        return Recipes_Filtered.size();
    }

    //Filter call
    @Override
    public Filter getFilter() {
        return filterInstance;
    }

    //This Filter will bring up needed methods
    private Filter filterInstance = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            //This will later copied into our filtered list.
            ArrayList<IRecipeModel> filteredList = new ArrayList<>();

            //No constraints/filter found so we add all values into list.
            if (constraint == null || constraint.length() == 0)
                filteredList = new ArrayList<>(Recipes_Original);
            else
            {
                //Removing empty spaces and making the constraint case insensitive
                String passedInFilter = constraint.toString().toLowerCase().trim();

                //Now we check each recipe in the main list to see if it matches.
                //Using a for each statement
                for (IRecipeModel recipe : Recipes_Original)
                {
                    //Once again make it lower case and check if it contains the filter.
                    //Right now we have filtering by Title and by Quick Description
                    if (recipe.getRecipeTitle().toLowerCase().contains(passedInFilter) || recipe.getRecipeQuickDescription().toLowerCase().contains(passedInFilter))
                        filteredList.add(recipe);

                        //Filter by tags, else to avoid adding repeats, inside the for loop
                        //we call the outer loop's recipe and check through each of its tags
                    else {
                        for (int i = 0; i < recipe.getTags().size(); i++) {
                            if (recipe.getTags().get(i).toLowerCase().contains(passedInFilter))
                                filteredList.add(recipe);
                        }
                    }
                }
            }

            //Since method returns FilterResults
            FilterResults results = new FilterResults();
            //We store filteredList in results.
            results.values = filteredList;

            //This return then goes into publish results down below.
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            //Assign the list we made in the last method to our filtered one.
            Recipes_Filtered = new ArrayList<IRecipeModel>((ArrayList)results.values);

            // when called, looks at what items are displayed on the screen at the moment of its call
            // (more precisely which row indexes ) and calls getView() with those positions.
            notifyDataSetChanged();
        }
    };
}