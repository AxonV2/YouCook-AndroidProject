package com.example.youcook.ui.create;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.youcook.R;
import com.example.youcook.context.BitmapHandler;
import com.example.youcook.context.MainActivity;
import com.example.youcook.controller.SQLiteDataHelper;
import com.example.youcook.models.ClassInstanceFactory;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.IUsersModel;
import com.example.youcook.ui.create.created_recipe_tag_recycler.CreateTagRecyclerViewHolder;
import com.example.youcook.ui.recipes.RecipesViewModel;

import java.util.ArrayList;

public class CreateViewModel extends ViewModel {

    //Created Recipe Variables
    //Public so live data in create fragment can work
    public MutableLiveData<String> createdRecipeTitle = new MutableLiveData<>();
    public MutableLiveData<String> createdRecipeLongDescription = new MutableLiveData<>();
    public MutableLiveData<String> createdRecipeQuickDescription = new MutableLiveData<>();
    //Set to string so it can work with live data
    public MutableLiveData<String> createdRecipePrep = new MutableLiveData<>();
    public MutableLiveData<String> createdRecipeCook = new MutableLiveData<>();
    public MutableLiveData<String> createdRecipeDone = new MutableLiveData<>();

    private static Bitmap createdRecipeImageBitmap;
    public static void setCreatedRecipeImage(Bitmap val) { createdRecipeImageBitmap = val;}

    //Errors for each field
    public MutableLiveData<String> Error = new MutableLiveData<>();
    public MutableLiveData<String> Error1 = new MutableLiveData<>();
    public MutableLiveData<String> Error2 = new MutableLiveData<>();
    public MutableLiveData<String> Error3 = new MutableLiveData<>();
    public MutableLiveData<String> Error4 = new MutableLiveData<>();
    public MutableLiveData<String> Error5 = new MutableLiveData<>();
    public MutableLiveData<String> Error6 = new MutableLiveData<>();
    public MutableLiveData<String> Error7 = new MutableLiveData<>();

    public ArrayList<MutableLiveData<String>> Errors = new ArrayList<MutableLiveData<String>>(){{
    add(Error); add(Error1); add(Error3); add(Error4); add(Error5); add(Error6); }};

    public CreateViewModel() { }

    //Called from button in create fragment
    public void CreateRecipe()
    {
        //Get the tags
        ArrayList<String> chosenTags = new ArrayList<>(CreateTagRecyclerViewHolder.getChosenTags());

        //Get list size
        int LatestRecipe = IRecipeModel.Full_Recipe_List.size() - 1;
        //Get next ID using list size and ID getter
        Integer LatestID = IRecipeModel.Full_Recipe_List.get(LatestRecipe).getRecipeID() + 1;

        //Log.d("Tag", "Recipe: " + LatestID);


        //Errors for each input field
        Error.setValue(null); Error1.setValue(null); Error2.setValue(null);
        Error3.setValue(null); Error4.setValue(null); Error5.setValue(null);
        Error6.setValue(null);

        if (createdRecipeTitle.getValue() == null || createdRecipeTitle.getValue().isEmpty() || createdRecipeTitle.getValue().trim().length() == 0)
            Error.setValue("Insert valid recipe Title");

        if (createdRecipeQuickDescription.getValue() == null || createdRecipeQuickDescription.getValue().isEmpty() || createdRecipeQuickDescription.getValue().trim().length() == 0)
            Error1.setValue("Insert valid quick Description");

        if (createdRecipeLongDescription.getValue() == null || createdRecipeLongDescription.getValue().isEmpty() || createdRecipeLongDescription.getValue().trim().length() == 0)
            Error2.setValue("Insert valid long Description");

        if (createdRecipePrep.getValue() == null || createdRecipePrep.getValue().isEmpty())
            Error3.setValue("Insert valid prep time");

        if (createdRecipeCook.getValue() == null || createdRecipeCook.getValue().isEmpty())
            Error4.setValue("Insert valid cook time");

        if (createdRecipeDone.getValue() == null || createdRecipeDone.getValue().isEmpty())
            Error5.setValue("Insert valid done time");

        if (chosenTags.size() == 0)
            Error6.setValue("Pick at least one tag for your recipe");

        if (MainActivity.imageView == null)
            Error7.setValue("Please insert a picture");
        else
            Error7.setValue(null);

        //Log.d("Tag", "List: " + chosenTags.size());

        //If any of the errors are still set then we return
        for (LiveData<String> ER: Errors)
            if (ER.getValue() != null)
                return;

        try
        {
            //Log.d("Tag", "Made it");

            String recipeTitle = createdRecipeTitle.getValue().trim();
            String recipeQuickDescription = createdRecipeQuickDescription.getValue().trim();
            String recipeLongDescription = createdRecipeLongDescription.getValue().trim();
            Integer recipePrep = Integer.valueOf(createdRecipePrep.getValue());
            Integer recipeCook = Integer.valueOf(createdRecipeCook.getValue());
            Integer recipeDone = Integer.valueOf(createdRecipeDone.getValue());

            String recipeImage = BitmapHandler.BitMapToString(createdRecipeImageBitmap);

            //Dummy user
            IUsersModel recipeAuthor = ClassInstanceFactory.UsersFactory("test3@gmail.com", "Test3", "https://cdn.fastly.picmonkey.com/contentful/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg");

            //Creating the recipe
            IRecipeModel recipeModel = ClassInstanceFactory.RecipeFactory(LatestID, recipeAuthor, recipeTitle, recipeImage, recipeLongDescription, recipeQuickDescription, recipePrep, recipeCook, recipeDone, chosenTags);

            //Adding to database and list
            SQLiteDataHelper.AddRecipe(recipeModel);
            IRecipeModel.Full_Recipe_List.add(recipeModel);

            //Setting up the viewModel for our created Recipe
            RecipesViewModel.setSelectedItem(recipeModel);

            //Log.d("Tag", "Casting：RecipeTitle - " + RecipeTitle);
            //Log.d("Tag", "Casting：RecipeQuickDescription - " + RecipeQuickDescription);
            //Log.d("Tag", "Casting：RecipeLongDescription - " + RecipeLongDescription);
            //Log.d("Tag", "Casting：RecipePrep - " + RecipePrep);
            //Log.d("Tag", "Casting：RecipeCook - " + RecipeCook);
            //Log.d("Tag", "Casting：RecipeDone - " + RecipeDone);

        } catch (Exception e)
        {
            Log.d("Tag", "Exception：" + e);
            return;
        }

        //After recipe is done we navigate to it;
        Navigation.findNavController(MainActivity.imageView).navigate(R.id.action_nav_create_to_nav_selected_recipe);
    }
}
