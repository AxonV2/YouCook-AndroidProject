package com.example.youcook.ui.recipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.youcook.controller.SQLiteDataHelper;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.RecipeModel;

import java.util.stream.Collectors;

public class RecipesViewModel extends ViewModel {

    //View Model will handle assignments and such with getters and setters

    //Recycler Variables
    private static MutableLiveData<Integer> selectedRecipeItemID = new MutableLiveData<Integer>();
    private static Integer selectedRecipeTagsSize;

    //Getters for Recycler Variables
    public static LiveData<Integer> getSelectedRecipeItemID() { return selectedRecipeItemID; }
    public static Integer getTagListSize() { return selectedRecipeTagsSize; }


    //Setter for selectedRecipeItemID that also assigns every variable
    public static void setSelectedItem(Integer item)
    {
        selectedRecipeItemID.setValue(item);

        //Can't tell if this is good or bad
        //Lambda used to get instance of passed in ID;
        IRecipeModel InstanceFromID = RecipeModel.Full_Recipe_List.stream().filter(x -> x.getRecipeID().equals(selectedRecipeItemID.getValue())).collect(Collectors.toList()).get(0);

        //Get tag size for Recycler
        selectedRecipeTagsSize = InstanceFromID.getTags().size();

        //Recipe Details
        mutableRecipeTitle.setValue(InstanceFromID.getRecipeTitle());
        mutableRecipeQuickDescription.setValue(InstanceFromID.getRecipeQuickDescription());
        mutableRecipeLongDescription.setValue(InstanceFromID.getRecipeLongDescription());
        mutableRecipePrep.setValue(InstanceFromID.getPrepTime());
        mutableRecipeCook.setValue(InstanceFromID.getCookTime());
        mutableRecipeDone.setValue(InstanceFromID.getDoneTime());
        mutableRecipeImage.setValue(InstanceFromID.getRecipeImageURL());

         //Author Details
        mutableRecipeAuthorEmail.setValue(InstanceFromID.getRecipeAuthor().getUserEmail());
        mutableAuthorImage.setValue(InstanceFromID.getRecipeAuthor().getPictureURL());
        mutableRecipeAuthorName.setValue(InstanceFromID.getRecipeAuthor().getUserName());

        UpdateFavoritesButton();

    }

    //Constructor
    public RecipesViewModel() { }


    public static void UpdateFavoritesButton()
        {
        //Check if in favorites
        //TODO CHANGE TO LOGGED IN USER
        String CurrentUserEmail = mutableRecipeAuthorEmail.getValue();
        Integer CurrentRecipe =  selectedRecipeItemID.getValue();
        if(SQLiteDataHelper.InFavorites(CurrentUserEmail, CurrentRecipe))
            favoriteButtonMutable.setValue("Remove from favorites");
        else
            favoriteButtonMutable.setValue("Add to favorites");
    }


    //In Favorites?
    private static MutableLiveData<String> favoriteButtonMutable = new MutableLiveData<String>();
    public static MutableLiveData<String> getFavoritesButton() { return favoriteButtonMutable; }

    //Full Recipe Displays
    private static MutableLiveData<String> mutableRecipeImage = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableAuthorImage = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeTitle = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeLongDescription = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeQuickDescription = new MutableLiveData<String>();
    private static MutableLiveData<Integer> mutableRecipePrep = new MutableLiveData<Integer>();
    private static MutableLiveData<Integer> mutableRecipeCook = new MutableLiveData<Integer>();
    private static MutableLiveData<Integer> mutableRecipeDone = new MutableLiveData<Integer>();
    private static MutableLiveData<String> mutableRecipeAuthorName = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeAuthorEmail = new MutableLiveData<String>();

    //Full recipe display getters for XML Live Data
    public static LiveData<String> getRecipeTitle() { return mutableRecipeTitle; }
    public static LiveData<String> getRecipeQuickDescription() { return mutableRecipeQuickDescription; }
    public static LiveData<String> getRecipeLongDescription() { return mutableRecipeLongDescription; }
    public static LiveData<Integer> getRecipePrep() { return mutableRecipePrep; }
    public static String getRecipePrepDisplay() { return "Prep " + mutableRecipePrep.getValue() + " Min(s)"; }
    public static LiveData<Integer> getRecipeCook() { return mutableRecipeCook; }
    public static String getRecipeCookDisplay() { return "Cook " + mutableRecipeCook.getValue() + " Min(s)"; }
    public static LiveData<Integer> getRecipeDone() { return mutableRecipeDone; }
    public static String getRecipeDoneDisplay() { return "Done " + mutableRecipeDone.getValue() + " Min(s)"; }
    public static LiveData<String> getRecipeAuthorName() { return mutableRecipeAuthorName; }
    public static LiveData<String> getRecipeAuthorEmail() { return mutableRecipeAuthorEmail; }
    public static String getAuthorImage() { return mutableAuthorImage.getValue(); }
    public static String getRecipeImage() { return mutableRecipeImage.getValue(); }
}