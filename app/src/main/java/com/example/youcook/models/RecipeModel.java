package com.example.youcook.models;

import java.util.ArrayList;

public class RecipeModel implements IRecipeModel
{
    private Integer recipeID = 0;
    private IUsersModel recipeAuthor;
    private String recipeTitle;
    private String recipeImageURL;
    private String recipeLongDescription;
    private String recipeQuickDescription;
    private Integer prepTime;
    private Integer cookTime;
    private Integer doneTime;
    private ArrayList<String> tags = new ArrayList<>();
    private Boolean isFavorite = false;

    //region GettersAndSetters

    //Getters
    @Override
    public Integer getRecipeID() { return recipeID; }
    @Override
    public Boolean getFavorite() { return isFavorite; }
    @Override
    public IUsersModel getRecipeAuthor() { return recipeAuthor; }
    @Override
    public String getRecipeTitle() { return recipeTitle; }
    @Override
    public String getRecipeImageURL() { return recipeImageURL; }
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
    public void setFavorite(Boolean val) { isFavorite = val; }
    @Override
    public void setRecipeAuthor(IUsersModel author) { recipeAuthor = author; }
    @Override
    public void setRecipeTitle(String val) { recipeTitle = val; }
    @Override
    public void setRecipeImageURL(String val) { recipeImageURL = val; }
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

    public RecipeModel(Integer RecipeID, IUsersModel Author , String RecipeTitle,
                       String ImageURL, String recipeLongDescription, String RecipeQuickDescription , Integer PrepTime, Integer CookTime, Integer DoneTime, ArrayList<String> Tags)
    {
        this.recipeID = RecipeID;
        this.recipeAuthor = Author;
        this.recipeTitle = RecipeTitle;
        this.recipeImageURL = ImageURL;
        this.recipeLongDescription = recipeLongDescription;
        this.recipeQuickDescription = RecipeQuickDescription;
        this.prepTime = PrepTime;
        this.cookTime = CookTime;
        this.doneTime = DoneTime;
        this.tags.addAll(Tags);
    }
}
