package com.example.youcook.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.youcook.models.ClassInstanceFactory;
import com.example.youcook.models.IRecipeModel;
import com.example.youcook.models.IUsersModel;

import java.util.ArrayList;

import static com.example.youcook.context.MainActivity.YouCookDatabase;

public class SQLiteDataHelper
{
    /*
        SQLite creates a unique row id (rowid) automatically.
        This field is usually left out when you use "select * ...", but you can fetch this id by using "select rowid,* ...".
        Be aware that according to the SQLite documentation, they discourage the use of autoincrement.

        create table myTable ( code text, description text );
        insert into myTable values ( 'X', 'some descr.' );
        select rowid, * from myTable;
     */


    private static SQLiteDatabase ReadableDB = YouCookDatabase.getReadableDatabase();
    private static SQLiteDatabase WritableDB = YouCookDatabase.getWritableDatabase();

    public static Integer FavoritesTableHandling(String AuthorEmail, Integer RecipeID)
    {
        ContentValues cv = new ContentValues();

        String FavoritesQuery = "SELECT * FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";

        //Values for constraints
        Cursor FavoritesResult = ReadableDB.rawQuery(FavoritesQuery,new String[] { AuthorEmail, String.valueOf(RecipeID)});

        //If no results are found
        if (!FavoritesResult.moveToFirst())
        {
            //Add them in
            cv.put("UserEmail", AuthorEmail );
            cv.put("RecipeID", RecipeID);
            WritableDB.insert(SQLiteDataMain.USER_FAVORITES_TABLE, null, cv);

            FavoritesResult.close();

            return 0;
        }
        else
        {
            //If it already exists remove it
            String removeQuery = "DELETE FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";

            //Values for constraints
            WritableDB.execSQL(removeQuery,new String[] { AuthorEmail, String.valueOf(RecipeID)});

            FavoritesResult.close();

            return 1;
        }
    }



    public static void GiveValues()
    {
        //region Additions

        //Add Users
        AddUser(ClassInstanceFactory.UsersFactory("test1@gmail.com", "Test1", "https://upload.wikimedia.org/wikipedia/commons/5/59/That_Poppy_profile_picture.jpg"));
        AddUser(ClassInstanceFactory.UsersFactory("test2@gmail.com", "Test2", "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1"));
        AddUser(ClassInstanceFactory.UsersFactory("test3@gmail.com", "Test3", "https://cdn.fastly.picmonkey.com/contentful/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg"));

        //Add Tags
        ArrayList<String> Tags = new ArrayList<String>(){
            {
                add("Healthy"); add("Low Carb"); add("High Protein"); add("Low Fat"); add("Vegetarian"); add("Meaty"); add("Soy"); add("Easily Made");
                add("Quick"); add("Soup"); add("Dessert"); add("Hangover Food"); add("Cold");
            } };

        for (String Tag : Tags) { AddTagsToDB(Tag); }


        //Add Recipes and connections
        AddRecipe(ClassInstanceFactory.RecipeFactory(1, GetUser("test1@gmail.com") ,"Soup", "https://i.imgur.com/DeGj9h1.jpg", "STEP 1\n" +
                        "Heat a large saucepan and dry-fry 2 tsp cumin seeds and a pinch of chilli flakes for 1 min, or until they start to jump around the pan and release their aromas.\n" +
                        "\n" +
                        "STEP 2\n" +
                        "Scoop out about half with a spoon and set aside. Add 2 tbsp olive oil, 600g coarsely grated carrots, 140g split red lentils, 1l hot vegetable stock and 125ml milk to the pan and bring to the boil.\n" +
                        "\n" +
                        "STEP 3\n" +
                        "Simmer for 15 mins until the lentils have swollen and softened.\n" +
                        "\n" +
                        "STEP 4\n" +
                        "Whizz the soup with a stick blender or in a food processor until smooth (or leave it chunky if you prefer).\n" +
                        "\n" +
                        "STEP 5\n" +
                        "Season to taste and finish with a dollop of plain yogurt and a sprinkling of the reserved toasted spices. Serve with warmed naan breads.",
                "Healthy Vegetable Soup!", 25, 50, 25+50,
                new ArrayList<String>(){{ add("Healthy"); add("Soup"); }}));

        AddRecipe(ClassInstanceFactory.RecipeFactory(2,GetUser("test2@gmail.com") ,"The Hike", "https://images.immediate.co.uk/production/volatile/sites/22/2018/09/JI_270217_HikingRecipes_159-9074ec1.jpg?quality=90&resize=768,574", "Preheat the oven to 180°C / 350°F. Lightly grease a large rectangular baking dish (approximately 9\" x 13\" / 23 x 33 cm) and line with a piece of parchment paper (with overhang so the bars are easier to remove.)\n" +
                        "\nIn a large bowl, mash the banana until smooth. Stir in the vanilla.\n" +
                        "\nAdd the remaining ingredients to the banana mixture and stir until fully combined. You'll end up with a heavy thick mixture.\n" +
                        "\nSpoon the mixture into the prepared dish. With lightly wet hands, smooth out until even and uniform. Press down on the dough until compacted.\n" +
                        "\nBake for 22 to 26 minutes, until firm and lightly golden along the edges.\n" +
                        "\nPlace the dish on a cooling rack for 10 minutes, then carefully slide a knife to loosen the ends and gently lift out. Place the slab on a cooling rack until completely cool.\n" +
                        "\nOnce cool, slice into bars. I like to use a pizza slicer as it easily cuts through the dried fruit and nuts. A bread knife also works well. Leftovers can be wrapped up and stored in the fridge for a week, or stored in the freezer for 4 to 6 weeks.",
                "Healthy Vegetable Soup!", 2, 10, 2+10,
                new ArrayList<String>(){{ add("Low Carb"); add("Healthy"); add("Soup"); add("Quick"); add("Cold"); }}));
        AddRecipe(ClassInstanceFactory.RecipeFactory(3,GetUser("test3@gmail.com") ,"Tall Slim Potatoes", "https://www.eatwell101.com/wp-content/uploads/2018/05/Garlic-Butter-Chicken-and-Potatoes-Skillet.jpg", "STEP 1\n" +
                        "Put a roasting tin in the oven (one big enough to take the potatoes in a single layer) and heat oven to 200C/fan 180C/gas 6.\n" +
                        "\n" +
                        "STEP 2\n" +
                        "Peel 1kg potatoes and cut each into 4 even-sized pieces if they are medium size, 2-3 if smaller (5cm pieces).\n" +
                        "\n" +
                        "STEP 3\n" +
                        "Drop the potatoes into a large pan and pour in enough water to barely cover them.\n" +
                        "\n" +
                        "STEP 4\n" +
                        "Add salt, then wait for the water to boil. As soon as the water reaches a full rolling boil, lower the heat, put your timer on and simmer the potatoes uncovered, reasonably vigorously, for 2 mins.\n" +
                        "\n" +
                        "STEP 5\n" +
                        "Meanwhile, put 100g duck or goose fat or 100ml olive oil into the hot roasting tin and heat it in the oven for a few mins, so it’s really hot.\n" +
                        "\n" +
                        "STEP 6\n" +
                        "Drain the potatoes in a colander then shake the colander back and forth a few times to fluff up the outsides.\n" +
                        "\n" +
                        "STEP 7\n" +
                        "Sprinkle with 2 tsp flour and give another shake or two so they are evenly and thinly coated.\n" +
                        "\n" +
                        "STEP 8\n" +
                        "Carefully put the potatoes into the hot fat – they will sizzle as they go in – then turn and roll them around so they are coated all over.\n" +
                        "\n" +
                        "STEP 9\n" +
                        "Spread them in a single layer making sure they have plenty of room.\n" +
                        "\n" +
                        "STEP 10\n" +
                        "Roast the potatoes for 15 mins, then take them out of the oven and turn them over.\n" +
                        "\n" +
                        "STEP 11\n" +
                        "Roast for another 15 mins and turn them over again. Put them back in the oven for another 10-20 mins, or however long it takes to get them really golden and crisp. The colouring will be uneven, which is what you want.\n" +
                        "\n" +
                        "STEP 12\n" +
                        "Scatter with Maldon salt and serve straight away.",
                "Simple potato recipe", 0, 150, 150,
                new ArrayList<String>(){{ add("Vegetarian"); add("Healthy"); add("Easily Made"); add("High Protein"); add("Soy"); add("Low Fat");}}));

        //endregion

        //Finally populate list to use.
        IRecipeModel.Full_Recipe_List.addAll(GetRecipesList());
    }


    public static void AddTagsToDB(String TagText)
    {
        ContentValues cv = new ContentValues();

        //Check if value already exists (tag)
        String TagCheck = "SELECT TagText FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE TagText = ?";


        Cursor TagResults = ReadableDB.rawQuery(TagCheck,new String[] {TagText});

        //If no results found tags get added
        if (!TagResults.moveToFirst())
        {
            cv.put("TagText", TagText);
            WritableDB.insert(SQLiteDataMain.TAGS_TABLE, null, cv);
        }

        TagResults.close();
    }


    public static void AddUser(IUsersModel User)
    {
        //Need instance to add values to specified table.
        ContentValues cv = new ContentValues();

        //Check if value already exists (Email)
        String UserCheck = "SELECT Email FROM " + SQLiteDataMain.USERS_TABLE + " WHERE Email = ?";

        //Query Result with Email parameter
        Cursor UserResults = ReadableDB.rawQuery(UserCheck,new String[] {User.getUserEmail()});

        //If no results found Users get added
        if (!UserResults.moveToFirst())
        {
            //Associative Array like in PHP
            //Database Column + Value
            cv.put("Email", User.getUserEmail());
            cv.put("UserName", User.getUserName());
            cv.put("ImageURL", User.getPictureURL());

            //Insert into Database.
            //Database.insert(Table Name, nullColumnHack, ContentValues)
            //Writable Database locks database so no other processes can update.
            WritableDB.insert(SQLiteDataMain.USERS_TABLE, null, cv);

            //region NullColumnHack Description
        /*
            nullColumnHack optional; may be null. SQL doesn't allow inserting a completely empty row without naming at least one column name.
            If your provided values is empty, no column names are known and an empty row can't be inserted.
            If not set to null, the nullColumnHack parameter provides the name of nullable column name to explicitly insert a NULL into in the case where your values is empty.

            Basically if we're trying to insert an empty row we need to fill this out.
        */
            //endregion

            //OperationCheck will return -1 if failed.
        }

        UserResults.close();
    }

    public static void AddRecipe(IRecipeModel Recipe)
    {

        ContentValues cv = new ContentValues();

        //Check if value already exists (Email)
        String RecipeCheck = "SELECT ID FROM " + SQLiteDataMain.RECIPES_TABLE + " WHERE ID = ?";

        //Query Result
        Cursor RecipeResults = ReadableDB.rawQuery(RecipeCheck,new String[] {String.valueOf(Recipe.getRecipeID())});

        //If no results found recipes get added
        if (!RecipeResults.moveToFirst())
        {
            cv.put("AuthorEmail", Recipe.getRecipeAuthor().getUserEmail());
            cv.put("RecipeTitle", Recipe.getRecipeTitle());
            cv.put("ImageURL", Recipe.getRecipeImageURL());
            cv.put("RecipeLongDescription", Recipe.getRecipeLongDescription());
            cv.put("RecipeQuickDescription", Recipe.getRecipeQuickDescription());
            cv.put("PrepTime", Recipe.getPrepTime());
            cv.put("CookTime", Recipe.getCookTime());
            cv.put("DoneTime", Recipe.getDoneTime());

            WritableDB.insert(SQLiteDataMain.RECIPES_TABLE, null, cv);
            cv.clear();

            //Connection for each tag in database
            for (String Tag : Recipe.getTags())
            {
                cv.put("RecipeID", Recipe.getRecipeID());
                cv.put("TagID", GetTagID(Tag));
                WritableDB.insert(SQLiteDataMain.RECIPE_TAGS_TABLE, null, cv);

                cv.clear();
            }
        }

        RecipeResults.close();
    }

    public static ArrayList<IRecipeModel> GetRecipesList()
    {
        //List of recipes to return
        ArrayList<IRecipeModel> returnList = new ArrayList<>();

        //SQL Query
        String query = "SELECT * FROM " + SQLiteDataMain.RECIPES_TABLE;

        //Readable Database not Writable
        //raw Query returns Cursor (an array of results).
        Cursor result = ReadableDB.rawQuery(query,null);

        //Boolean if there is a first
        if (result.moveToFirst())
        {
            //While there are results
            do {
                //Get variables
                Integer recipeID = result.getInt(0);
                String recipeAuthorEmail = result.getString(1);
                String recipeTitle = result.getString(2);
                String recipeImage = result.getString(3);
                String recipeLongDescription = result.getString(4);
                String recipeQuickDescription = result.getString(5);
                Integer prepTime = result.getInt(6);
                Integer cookTime = result.getInt(7);
                Integer doneTime = result.getInt(8);

                //Assign to Model through factory
                IRecipeModel recipeModel = ClassInstanceFactory.RecipeFactory(recipeID, GetUser(recipeAuthorEmail),
                        recipeTitle, recipeImage, recipeLongDescription, recipeQuickDescription,prepTime,cookTime,doneTime, GetTags(recipeID));

                if (InFavorites(recipeModel.getRecipeAuthor().getUserEmail(), recipeModel.getRecipeID()))
                {
                    recipeModel.setFavorite(true);
                    IRecipeModel.Favorites_Recipe_List.add(recipeModel);
                }

                //Add to return List.
                returnList.add(recipeModel);

            } while(result.moveToNext());
        }
        else {
            result.close();
        }

        //Close cursor
        result.close();
        //Return List
        return returnList;
    }

    public static ArrayList<String> GetTags(Integer RecipeID)
    {
        //Returning Tags List
        ArrayList<String> recipeTags = new ArrayList<String>();

        //Check Recipe + Tags table for associations
        String RecipeTagLink = "SELECT TagID FROM " + SQLiteDataMain.RECIPE_TAGS_TABLE + " WHERE RecipeID = ?";

        //Query Result and parameter
        Cursor IDResults = ReadableDB.rawQuery(RecipeTagLink,new String[] {String.valueOf(RecipeID)});

        if (IDResults.moveToFirst())
        {
            Cursor tagResult;
            //Foreach ID from IDResults
            do {
                //Add tag text that matches ID
                String tagQuery = "SELECT TagText FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE ID = ?";

                //Pass in TagID from previous cursor
                tagResult = ReadableDB.rawQuery(tagQuery,new String[] {String.valueOf(IDResults.getInt(0))});

                if (tagResult.moveToFirst())
                {
                    //Get text and add it to return list
                    recipeTags.add(tagResult.getString(0));
                }

            } while (IDResults.moveToNext());

            //Close inner cursor
            tagResult.close();
        }

        //Close cursor
        IDResults.close();

        return recipeTags;
    }

    public static ArrayList<String> GetAvailableTags()
    {
        //Returning list for tag selection
        ArrayList<String> allTags = new ArrayList<>();

        String tagQuery = "SELECT * FROM " + SQLiteDataMain.TAGS_TABLE;
        Cursor results = ReadableDB.rawQuery(tagQuery,null);

        //If results are found
        if (results.moveToFirst())
        {
            do {
                allTags.add(results.getString(1));
            } while (results.moveToNext());
        }

        results.close();
        return allTags;
    }

    private static Integer GetTagID(String Tag)
    {
        String tagQuery = "SELECT * FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE TagText = ?";

        //Query Result
        Cursor tagResult = ReadableDB.rawQuery(tagQuery, new String[] { Tag });

        if (tagResult.moveToFirst())
        {
            //Save value so we can close cursor
            Integer TagID = tagResult.getInt(0);

            tagResult.close();

            return TagID;
        }

        tagResult.close();
        return 0;

    }

    private static IUsersModel GetUser(String Email) {
        //Check for Email
        String UserCheck = "SELECT * FROM " + SQLiteDataMain.USERS_TABLE + " WHERE Email = ?";

        //Query Result with Email parameter
        Cursor UserResults = ReadableDB.rawQuery(UserCheck, new String[]{Email});

        if (UserResults.moveToFirst()) {
            String UserEmail = UserResults.getString(0);
            String UserName = UserResults.getString(1);
            String UserImageURL = UserResults.getString(2);

            UserResults.close();

            return ClassInstanceFactory.UsersFactory(UserEmail, UserName, UserImageURL);
        }

        UserResults.close();

        return ClassInstanceFactory.UsersFactory("", "", "");
    }

    public static Boolean InFavorites(String AuthorEmail, Integer RecipeID)
    {
        String FavoritesQuery = "SELECT * FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";
        Cursor FavoritesResult = ReadableDB.rawQuery(FavoritesQuery,new String[] { AuthorEmail, String.valueOf(RecipeID)});

        //Assign value here so we can close cursor before returning

        //If NOT FOUND -> not in favorites
        //If FOUND -> in favorites
        Boolean result = FavoritesResult.moveToFirst();

        FavoritesResult.close();

        return result;
    }
}
