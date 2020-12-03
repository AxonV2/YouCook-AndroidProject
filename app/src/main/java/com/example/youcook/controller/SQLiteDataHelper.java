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
    //View to Model to Controller
    //In this case ViewModel -> Model -> Controller.  And backwards.
    /*
        SQLite creates a unique row id (rowid) automatically.
        This field is usually left out when you use "select * ...", but you can fetch this id by using "select rowid,* ...".
        Be aware that according to the SQLite documentation, they discourage the use of autoincrement.

        create table myTable ( code text, description text );
        insert into myTable values ( 'X', 'some descr.' );
        select rowid, * from myTable;
     */

    //Making this return a boolean to check if operation went through.

    private static SQLiteDatabase ReadableDB = YouCookDatabase.getReadableDatabase();
    private static SQLiteDatabase WritableDB = YouCookDatabase.getWritableDatabase();

    public static Integer FavoritesTableHandling(String AuthorEmail, Integer RecipeID)
    {
        ContentValues cv = new ContentValues();

        String FavoritesQuery = "SELECT * FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";

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
        else {
            //If it already exists remove it
            String removeQuery = "DELETE FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";
            WritableDB.execSQL(removeQuery,new String[] { AuthorEmail, String.valueOf(RecipeID)});
            FavoritesResult.close();
            return 1;
        }
    }

    public static Boolean InFavorites(String AuthorEmail, Integer RecipeID)
    {
        String FavoritesQuery = "SELECT * FROM " + SQLiteDataMain.USER_FAVORITES_TABLE + " WHERE UserEmail = ? AND RecipeID = ?";
        Cursor FavoritesResult = ReadableDB.rawQuery(FavoritesQuery,new String[] { AuthorEmail, String.valueOf(RecipeID)});

        //Assign value here so we can close cursor before returning
        Boolean result = FavoritesResult.moveToFirst();

        //If first NOT FOUND -> not in favorites
        //If first FOUND -> in favorites
        FavoritesResult.close();
        return result;
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
               add("Quick"); add("Cringe"); add("Gay"); add("Soup"); add("The Hike"); add("Dessert"); add("Hangover Food"); add("Cold");
            } };

        for (String Tag : Tags) { AddTagsToDB(Tag); }


        //Add Recipes and connections
        AddRecipe(ClassInstanceFactory.RecipeFactory(1, GetUser("test1@gmail.com") ,"Soup", "https://i.imgur.com/exel7MP.jpg", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "Healthy Vegetable Soup!", 25, 50, 25+50,
                new ArrayList<String>(){{ add("Healthy"); add("Soup"); add("The Hike"); add("Gay");}}));

        AddRecipe(ClassInstanceFactory.RecipeFactory(2,GetUser("test2@gmail.com") ,"The Hike", "https://media.discordapp.net/attachments/753353251319185410/783874219909185596/20190913_234837-1.jpg", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "Healthy Vegetable Soup!", 2, 10, 2+10,
                new ArrayList<String>(){{ add("Cringe"); add("Meaty"); add("The Hike"); add("Gay");}}));
        AddRecipe(ClassInstanceFactory.RecipeFactory(3,GetUser("test3@gmail.com") ,"Tall Slim Bastard", "https://media.discordapp.net/attachments/631207789020184577/782557872147398676/cock.png", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "HvH Prime Minister (Self-Proclaimed)", 0, 150, 150,
                new ArrayList<String>(){{ add("Vegetarian"); add("Healthy"); add("Easily Made"); add("High Protein"); add("Soy"); add("Low Fat");}}));

        //endregion

        //Finally populate list to use.
        IRecipeModel.Full_Recipe_List.addAll(getRecipesList());

    }


    public static void AddTagsToDB(String TagText)
    {
        ContentValues cv = new ContentValues();

        //Check if value already exists (Email)
        String TagCheck = "SELECT TagText FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE TagText = ?";

        //Query Result with Email parameter
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
            //insert(Table Name, nullColumnHack, ContentValues)
            //Writable Database locks database so no other processes can update.
            long OperationCheck = WritableDB.insert(SQLiteDataMain.USERS_TABLE, null, cv);

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

        //Query Result with Email parameter
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

            //We have to make a connection for each tag
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

    public static ArrayList<IRecipeModel> getRecipesList()
    {
        //List of recipes to return
        ArrayList<IRecipeModel> returnList = new ArrayList<>();

        //SQL Query
        String query = "SELECT * FROM " + SQLiteDataMain.RECIPES_TABLE;


        //Readable Database not Writable
        //raw Query returns Cursor (an array of results).
        Cursor result = ReadableDB.rawQuery(query,null);

        //Boolean if there is a first
        if (result.moveToFirst()) {
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

                //Assign to Model
                IRecipeModel recipeModel = ClassInstanceFactory.RecipeFactory(recipeID, GetUser(recipeAuthorEmail),
                        recipeTitle, recipeImage, recipeLongDescription, recipeQuickDescription,prepTime,cookTime,doneTime, GetTags(recipeID));


                //TODO CHECK IF CURRENT USER FAVORITES
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
        //Tags List
        ArrayList<String> recipeTags = new ArrayList<String>();

        //Check Recipe + Tags table for associations
        String RecipeTagLink = "SELECT TagID FROM " + SQLiteDataMain.RECIPE_TAGS_TABLE + " WHERE RecipeID = ?";

        //Query Result
        //Parameter passed in
        Cursor IDResults = ReadableDB.rawQuery(RecipeTagLink,new String[] {String.valueOf(RecipeID)});

        if (IDResults.moveToFirst())
        {
            Cursor tagResult;
            //Foreach ID from IDResults
            do {
                //Add tag text that matches ID
                String tagQuery = "SELECT TagText FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE ID = ?";

                //Parameter passed TagID
                tagResult = ReadableDB.rawQuery(tagQuery,new String[] {String.valueOf(IDResults.getInt(0))});
                    if (tagResult.moveToFirst())
                    {
                        //Get text and add it to return list
                        recipeTags.add(tagResult.getString(0));
                    }
            } while (IDResults.moveToNext());
            tagResult.close();
        }

        //Close cursor
        IDResults.close();

        return recipeTags;
    }

    public static void DeleteRecipeTest(Integer RecipeID)
    {
        String query = "DELETE FROM " + SQLiteDataMain.RECIPES_TABLE + " WHERE ID = " + RecipeID;
        WritableDB.execSQL(query,null);
    }


    private static Integer GetTagID(String Tag)
    {
        String tagQuery = "SELECT * FROM " + SQLiteDataMain.TAGS_TABLE + " WHERE TagText = ?";

        //Query Result
        Cursor tagResult = ReadableDB.rawQuery(tagQuery, new String[] { Tag });
            if (tagResult.moveToFirst())
                return tagResult.getInt(0);
            else
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
}
