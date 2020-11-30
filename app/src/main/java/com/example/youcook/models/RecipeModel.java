package com.example.youcook.models;

import java.util.ArrayList;

public class RecipeModel implements IRecipeModel
{
    static Integer counter = 0;
    Integer recipeID = 0;
    String authorName;
    String recipeTitle;
    String recipeLongDescription;
    String recipeQuickDescription;
    Integer prepTime;
    Integer cookTime;
    Integer doneTime;

    //TO DO IMAGE

    ArrayList<String> tags = new ArrayList<>();

    //region GettersAndSetters
    //Getters
    @Override
    public Integer getRecipeID() { return recipeID; }
    @Override
    public String getAuthorName() { return authorName; }
    @Override
    public String getRecipeTitle() { return recipeTitle; }
    @Override
    public String getRecipeLongDescription() { return recipeLongDescription; }
    @Override
    public String getRecipeQuickDescription() { return recipeQuickDescription; }
    @Override
    public Integer getPrepTime() { return prepTime; }
    @Override
    public Integer getCookTime() { return cookTime; }
    @Override
    public Integer getDoneTime() { return doneTime; }
    @Override
    public ArrayList<String> getTags() { return tags; }

    //Setters
    @Override
    public void setAuthorName(String val) { authorName = val; }
    @Override
    public void setRecipeTitle(String val) { recipeTitle = val; }
    @Override
    public void setRecipeLongDescription(String val) { recipeLongDescription = val; }
    @Override
    public void setRecipeQuickDescription(String val) { recipeQuickDescription = val; }
    @Override
    public void setPrepTime(Integer val) { prepTime = val; }
    @Override
    public void setCookTime(Integer val) { cookTime = val; }
    @Override
    public void setDoneTime(Integer val) { doneTime = val; }
    @Override
    public void setTags(ArrayList<String> val) { tags = val; }

    //endregion


    public RecipeModel(){

    }

    //Used for recipe list display.
    public RecipeModel(String RecipeTitle,String RecipeQuickDescriptionValue, Integer PrepTime, Integer CookTime, Integer DoneTime )
    {
        counter++;
        recipeID += counter;

        this.recipeTitle = RecipeTitle;
        this.recipeQuickDescription = RecipeQuickDescriptionValue;
        this.prepTime = PrepTime;
        this.cookTime = CookTime;
        this.doneTime = DoneTime;
    }


    //Used for full recipe display.
    public RecipeModel(String authorName, String RecipeTitle, String recipeLongDescription, String RecipeQuickDescriptionValue , Integer PrepTime, Integer CookTime, Integer DoneTime, ArrayList<String> Tags)
    {
        //Sending to upper constructor
        this(RecipeTitle, RecipeQuickDescriptionValue, PrepTime, CookTime, DoneTime);

        this.authorName = authorName;
        this.recipeLongDescription = recipeLongDescription;
        this.tags.addAll(Tags);
    }
}
