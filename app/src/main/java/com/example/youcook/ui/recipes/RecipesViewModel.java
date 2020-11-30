package com.example.youcook.ui.recipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.youcook.models.IRecipeModel;

import java.util.stream.Collectors;

public class RecipesViewModel extends ViewModel {

    //View Model will handle assignments and such with getters and setters

    //Recycler Variables
    private static MutableLiveData<Integer> selectedRecipeItemID = new MutableLiveData<Integer>();
    private static Integer selectedRecipeTagsSize;

    //Getters for Recycler Variables
    public static LiveData<Integer> getSelectedRecipeItemID() { return selectedRecipeItemID; }
    public static Integer getListSize() { return selectedRecipeTagsSize; }


    //Setter for selectedRecipeItemID that also assigns every variable
    public static void setSelectedItem(Integer item)
    {
        selectedRecipeItemID.setValue(item);

        //Can't tell if this is good or bad
        //Lambda used to get instance of passed in ID;
        IRecipeModel InstanceFromID = IRecipeModel.Full_Recipe_List.stream().filter(x -> x.getRecipeID().equals(selectedRecipeItemID.getValue())).collect(Collectors.toList()).get(0);

        //Get tag size for Recycler
        selectedRecipeTagsSize = InstanceFromID.getTags().size();
        //Also add everything else
         mutableRecipeTitle.setValue(InstanceFromID.getRecipeTitle());
         mutableRecipeQuickDescription.setValue(InstanceFromID.getRecipeQuickDescription());
         mutableRecipeLongDescription.setValue(InstanceFromID.getRecipeLongDescription());
         mutableRecipePrep.setValue(InstanceFromID.getPrepTime());
         mutableRecipeCook.setValue(InstanceFromID.getCookTime());
         mutableRecipeDone.setValue(InstanceFromID.getDoneTime());
         mutableRecipeAuthorName.setValue(InstanceFromID.getAuthorName());
    }

    //Constructor
    public RecipesViewModel() { }


    //Full Recipe Displays

    //Need to implement
    //private static MutableLiveData<String> mutableRecipeImage = new MutableLiveData<String>();
    //public static LiveData<String> getRecipeImage() { return mutableRecipeImage; }

    //private static MutableLiveData<String> mutableAuthorImage = new MutableLiveData<String>();
    //public static LiveData<String> getAuthorImage() { return mutableAuthorImage; }

    private static MutableLiveData<String> mutableRecipeTitle = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeQuickDescription = new MutableLiveData<String>();
    private static MutableLiveData<String> mutableRecipeLongDescription = new MutableLiveData<String>();
    private static MutableLiveData<Integer> mutableRecipePrep = new MutableLiveData<Integer>();
    private static MutableLiveData<Integer> mutableRecipeCook = new MutableLiveData<Integer>();
    private static MutableLiveData<Integer> mutableRecipeDone = new MutableLiveData<Integer>();
    private static MutableLiveData<String> mutableRecipeAuthorName = new MutableLiveData<String>();


    //Full recipe display getters for observer
    public static LiveData<String> getRecipeTitle() { return mutableRecipeTitle; }
    public static LiveData<String> getRecipeQuickDescription() { return mutableRecipeQuickDescription; }
    public static LiveData<String> getRecipeLongDescription() { return mutableRecipeLongDescription; }
    public static LiveData<Integer> getRecipePrep() { return mutableRecipePrep; }
    public static LiveData<Integer> getRecipeCook() { return mutableRecipeCook; }
    public static LiveData<Integer> getRecipeDone() { return mutableRecipeDone; }
    public static LiveData<String> getRecipeAuthorName() { return mutableRecipeAuthorName; }
}