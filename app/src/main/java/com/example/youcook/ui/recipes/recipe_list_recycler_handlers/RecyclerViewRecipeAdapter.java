package com.example.youcook.ui.recipes.recipe_list_recycler_handlers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youcook.R;
import com.example.youcook.models.IRecipeModel;

import java.util.ArrayList;

public class RecyclerViewRecipeAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements Filterable
{
    //Example variables.
    //This one will eventually point to our database collection AS A REFERENCE
    private ArrayList<IRecipeModel> Recipes_Original = new ArrayList<>(IRecipeModel.Full_Recipe_List);

    //Remember you do not want a reference to the list you want a fully new list.
    //This one will be filtered so items will be removed.
    static private ArrayList<IRecipeModel> Recipes_Filtered = new ArrayList<>(IRecipeModel.Full_Recipe_List);

    static public ArrayList<IRecipeModel> GetFilteredList(){ return Recipes_Filtered; }


    public RecyclerViewRecipeAdapter() { }

    //Constructor
    public RecyclerViewRecipeAdapter(ArrayList<IRecipeModel> PartialRecipesList)
    {
        //Not a new list this one will always point to the database list as a reference
        Recipes_Original = PartialRecipesList;

        //This works like C# does (Not a reference in this it's a new one)
        Recipes_Filtered = new ArrayList<>(PartialRecipesList);
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

    }

    @Override
    public int getItemCount()
    {
        //Will be set to FILTERED list size instead.
        return Recipes_Filtered.size();
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
                    //Filter by tags?

                    if (recipe.getRecipeTitle().toLowerCase().contains(passedInFilter) ||
                            recipe.getRecipeQuickDescription().toLowerCase().contains(passedInFilter))
                        filteredList.add(recipe);

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


            //IF LIST EMPTY CALL RECIPESVIEWMODEL LIVE TEXT AND CHANGE IT???


            //Partial_Recipes_Filtered.clear();
            //Partial_Recipes_Filtered.addAll((ArrayList)(results.values));
        }
    };

    @Override
    public Filter getFilter() {
        return filterInstance;
    }
}