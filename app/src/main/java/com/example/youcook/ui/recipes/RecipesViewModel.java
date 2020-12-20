package com.example.youcook.ui.recipes;

import androidx.annotation.NonNull;
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
    private static final  MutableLiveData<Integer> selectedRecipeItemID = new MutableLiveData<Integer>();
    private static Integer selectedRecipeTagsSize;

    //Getters for Recycler Variables
    public static LiveData<Integer> getSelectedRecipeItemID() { return selectedRecipeItemID; }
    public static Integer getTagListSize() { return selectedRecipeTagsSize; }

    //Constructor
    public RecipesViewModel() { }

    //Setter for selectedRecipeItemID that also assigns every variable
    public static void setSelectedItem(IRecipeModel passedRecipe)
    {
        selectedRecipeItemID.setValue(passedRecipe.getRecipeID());

        //Get tag size for Recycler
        selectedRecipeTagsSize = passedRecipe.getTags().size();

        //Recipe Details
        mutableRecipeTitle.setValue(passedRecipe.getRecipeTitle());
        mutableRecipeQuickDescription.setValue(passedRecipe.getRecipeQuickDescription());
        mutableRecipeLongDescription.setValue(passedRecipe.getRecipeLongDescription());
        mutableRecipePrep.setValue(passedRecipe.getPrepTime());
        mutableRecipeCook.setValue(passedRecipe.getCookTime());
        mutableRecipeDone.setValue(passedRecipe.getDoneTime());
        mutableRecipeImageURL.setValue(passedRecipe.getRecipeImageURL());

        //Author Details
        mutableRecipeAuthorEmail.setValue(passedRecipe.getRecipeAuthor().getUserEmail());
        mutableAuthorImage.setValue(passedRecipe.getRecipeAuthor().getPictureURL());
        mutableRecipeAuthorName.setValue(passedRecipe.getRecipeAuthor().getUserName());

        UpdateFavoritesButton();
    }

    public static void UpdateFavoritesButton()
    {
        //Check if in favorites
        String CurrentUserEmail = mutableRecipeAuthorEmail.getValue();
        Integer CurrentRecipe =  selectedRecipeItemID.getValue();

        if(SQLiteDataHelper.InFavorites(CurrentUserEmail, CurrentRecipe))
            favoriteButtonMutable.setValue("Remove from favorites");
        else
            favoriteButtonMutable.setValue("Add to favorites");
    }

    //In Favorites?
    private static final MutableLiveData<String> favoriteButtonMutable = new MutableLiveData<String>();
    public static MutableLiveData<String> getFavoritesButton() { return favoriteButtonMutable; }

    //Full Recipe Displays
    private static final  MutableLiveData<String> mutableRecipeImageURL = new MutableLiveData<String>();
    private static final MutableLiveData<String> mutableAuthorImage = new MutableLiveData<String>();
    private static final MutableLiveData<String> mutableRecipeTitle = new MutableLiveData<String>();
    private static final MutableLiveData<String> mutableRecipeLongDescription = new MutableLiveData<String>();
    private static final MutableLiveData<String> mutableRecipeQuickDescription = new MutableLiveData<String>();
    private static final MutableLiveData<Integer> mutableRecipePrep = new MutableLiveData<Integer>();
    private static final MutableLiveData<Integer> mutableRecipeCook = new MutableLiveData<Integer>();
    private static final MutableLiveData<Integer> mutableRecipeDone = new MutableLiveData<Integer>();
    private static final MutableLiveData<String> mutableRecipeAuthorName = new MutableLiveData<String>();
    private static final MutableLiveData<String> mutableRecipeAuthorEmail = new MutableLiveData<String>();

    //Full recipe display getters for XML Live Data
    public static LiveData<String> getRecipeTitle() { return mutableRecipeTitle; }
    public static LiveData<String> getRecipeQuickDescription() { return mutableRecipeQuickDescription; }
    public static LiveData<String> getRecipeLongDescription() { return mutableRecipeLongDescription; }
    public static String getRecipePrepDisplay() { return "Prep " + mutableRecipePrep.getValue() + " Min(s)"; }
    public static String getRecipeCookDisplay() { return "Cook " + mutableRecipeCook.getValue() + " Min(s)"; }
    public static String getRecipeDoneDisplay() { return "Done " + mutableRecipeDone.getValue() + " Min(s)"; }
    public static LiveData<String> getRecipeAuthorName() { return mutableRecipeAuthorName; }
    public static LiveData<String> getRecipeAuthorEmail() { return mutableRecipeAuthorEmail; }
    public static String getAuthorImage() { return mutableAuthorImage.getValue(); }
    public static String getRecipeImageURL() { return mutableRecipeImageURL.getValue(); }
}