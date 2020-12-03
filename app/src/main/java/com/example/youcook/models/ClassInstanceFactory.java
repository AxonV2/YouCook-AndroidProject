package com.example.youcook.models;

import java.util.ArrayList;

public class ClassInstanceFactory
{
    //Constructors to avoid creating to many dependencies.
    //Every class call will come from here.

    public static IRecipeModel RecipeFactory(Integer RecipeID, IUsersModel Author , String RecipeTitle,
                                             String ImageURL, String recipeLongDescription, String RecipeQuickDescription , Integer PrepTime, Integer CookTime, Integer DoneTime, ArrayList<String> Tags)
    {
        return new RecipeModel(RecipeID, Author, RecipeTitle, ImageURL, recipeLongDescription, RecipeQuickDescription , PrepTime, CookTime, DoneTime, Tags);
    }

    public static IUsersModel UsersFactory(String EmailValue, String UserNameValue, String PictureURLValue)
    {
        return new UsersModel(EmailValue, UserNameValue, PictureURLValue);
    }

}
