package com.example.youcook.models;

import java.util.ArrayList;

public interface IRecipeModel
{
    //List used for testing
    /*
    ArrayList<IRecipeModel> Partial_Recipe_List = new ArrayList<IRecipeModel>()
    {
        {
            add(new RecipeModel("Title 1", "Quick Description 1", 1,1,1));

            add(new RecipeModel("Title 2", "Quick Description 2", 2,2,2));

            add(new RecipeModel("Title 3", "Quick Description 3", 3,3,3));

            add(new RecipeModel("Title 4", "Quick Description 4", 4,4,4));

            add(new RecipeModel("Title 5", "Quick Description 5", 5,5,5));

            add(new RecipeModel("Title 6", "BRICK", 80,80,100));
        }
    };
     */


    ArrayList<IRecipeModel> Full_Recipe_List = new ArrayList<IRecipeModel>()
    {
        {
            add(new RecipeModel("Fat Cunt", "Soup with cock and balls", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " +
                    "\n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck"
                    ,"Healthy Vegetable Soup!", 25, 50, 25+50,
                new ArrayList<String>(){{ add("a"); add("Soup"); add("a"); add("Soup"); add("a"); add("Soup"); add("a"); add("Soup"); add("a"); add("Soup"); }}));

            add(new RecipeModel("Fat Cunt2", "Fish and fucking chips", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck \n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Something cool to read", 25, 50, 25+50,
                    new ArrayList<String>(){{ add("f"); add("a"); }}));

            add(new RecipeModel("Fat Cunt3", "Soup with cock and balls", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n fuck", "Cinder block hitting you on the head \n cunt", 25, 50, 25+50,
                    new ArrayList<String>(){{ add("Vegetarian"); add("HVH"); add("Prime Minister"); add("Dog"); add("Legend"); add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); }}));
        }
    };

    //Getters

    Integer getRecipeID();

    String getAuthorName();

    String getRecipeTitle();

    String getRecipeLongDescription();

    String getRecipeQuickDescription();

    Integer getPrepTime();

    Integer getCookTime();

    Integer getDoneTime();

    ArrayList<String> getTags();

    //Setters
    void setAuthorName(String val);

    void setRecipeTitle(String val);

    void setRecipeLongDescription(String val);

    void setRecipeQuickDescription(String val);

    void setPrepTime(Integer val);

    void setCookTime(Integer val);

    void setDoneTime(Integer val);

    void setTags(ArrayList<String> val);
}
