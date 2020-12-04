package com.example.youcook.models;

import com.example.youcook.context.MainActivity;
import com.example.youcook.controller.SQLiteDataHelper;
import com.example.youcook.controller.SQLiteDataMain;

import java.util.ArrayList;

public interface IRecipeModel
{
    //Set to Database Values
    ArrayList<IRecipeModel> Full_Recipe_List = new ArrayList<IRecipeModel>();
    ArrayList<IRecipeModel> Favorites_Recipe_List = new ArrayList<IRecipeModel>();

    //region Getters and Setters

    //Getters
    Integer getRecipeID();

    Boolean getFavorite();

    IUsersModel getRecipeAuthor();

    String getRecipeTitle();

    String getRecipeImageURL();

    String getRecipeLongDescription();

    String getRecipeQuickDescription();

    Integer getPrepTime();

    Integer getCookTime();

    Integer getDoneTime();

    ArrayList<String> getTags();


    //Setters
    void setFavorite(Boolean val);

    void setRecipeAuthor(IUsersModel author);

    void setRecipeTitle(String val);

    void setRecipeImageURL(String val);

    void setRecipeLongDescription(String val);

    void setRecipeQuickDescription(String val);

    void setPrepTime(Integer val);

    void setCookTime(Integer val);

    void setDoneTime(Integer val);

    void setTags(ArrayList<String> val);

    //endregion
}
