package com.example.youcook.ui.create;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

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
import com.example.youcook.models.RecipeModel;
import com.example.youcook.models.UsersModel;
import com.example.youcook.ui.create.created_recipe_tag_recycler.CreateTagRecyclerViewHolder;
import com.example.youcook.ui.recipes.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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


    private String RecipeTitle;
    private String RecipeQuickDescription;
    private String RecipeLongDescription;
    private String RecipeImage;
    private Integer RecipePrep;
    private Integer RecipeCook;
    private Integer RecipeDone;

    //Have to see how to pull off
    private static MutableLiveData<String> createdRecipeImage = new MutableLiveData<>();
    public static MutableLiveData<String> getCreatedRecipeImage() { return getCreatedRecipeImage();}

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

    public CreateViewModel() { }

    //Called from button in create fragment
    public void CreateRecipe()
    {
        ArrayList<String> chosenTags = new ArrayList<>(CreateTagRecyclerViewHolder.getChosenTags());
        Integer LatestID = IRecipeModel.Full_Recipe_List.get(IRecipeModel.Full_Recipe_List.size() - 1).getRecipeID() + 1;
        Log.d("Tag", "Recipe: " + LatestID);
        BitmapHandler bt = new BitmapHandler();
        RecipeImage = bt.BitMapToString(createdRecipeImageBitmap);

        if (createdRecipeTitle.getValue() == null || createdRecipeTitle.getValue().isEmpty())
            Error.setValue("Insert valid recipe Title");
        if (createdRecipeQuickDescription.getValue() == null || createdRecipeQuickDescription.getValue().isEmpty())
            Error1.setValue("Insert valid quick Description");
        if (createdRecipeLongDescription.getValue() == null || createdRecipeLongDescription.getValue().isEmpty())
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
        {
            Log.d("Tag", "sexo: ");
            return;
        }


        //Log.d("Tag", "List: " + chosenTags.size());

        try
        {
            RecipeTitle = createdRecipeTitle.getValue();
            RecipeQuickDescription = createdRecipeQuickDescription.getValue();
            RecipeLongDescription = createdRecipeLongDescription.getValue();
            RecipePrep = Integer.valueOf(createdRecipePrep.getValue());
            RecipeCook = Integer.valueOf(createdRecipeCook.getValue());
            RecipeDone = Integer.valueOf(createdRecipeDone.getValue());


            //Dummy user
            IUsersModel recipeAuthor = ClassInstanceFactory.UsersFactory("test3@gmail.com", "Test3", "https://cdn.fastly.picmonkey.com/contentful/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg");

            //Creating the recipe
            IRecipeModel recipeModel = ClassInstanceFactory.RecipeFactory(LatestID, recipeAuthor, RecipeTitle, RecipeImage, RecipeLongDescription, RecipeQuickDescription, RecipePrep, RecipeCook, RecipeDone, chosenTags);
            //Adding to list
            IRecipeModel.Full_Recipe_List.add(recipeModel);
            //Adding to database
            SQLiteDataHelper.AddRecipe(recipeModel);

            //Log.d("Tag", "Casting：RecipeTitle - " + RecipeTitle);
            //Log.d("Tag", "Casting：RecipeQuickDescription - " + RecipeQuickDescription);
            //Log.d("Tag", "Casting：RecipeLongDescription - " + RecipeLongDescription);
            //Log.d("Tag", "Casting：RecipePrep - " + RecipePrep);
            //Log.d("Tag", "Casting：RecipeCook - " + RecipeCook);
            //Log.d("Tag", "Casting：RecipeDone - " + RecipeDone);
        }catch (Exception e)
        {
            Log.d("Tag", "Exception：" + e);
            return;
        }

        //Setting up the viewModel for our created Recipe
        RecipesViewModel.setSelectedItem(LatestID);

        //After recipe is done we navigate to it;
        //Navigation.findNavController().navigate(R.id.nav_home);

    }
}
