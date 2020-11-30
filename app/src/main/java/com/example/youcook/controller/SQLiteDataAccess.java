package com.example.youcook.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.youcook.models.IUsersModel;

public class SQLiteDataAccess extends SQLiteOpenHelper
{
    //Static remains the same no matter the instance
    //Final can only be assigned once,
    //remember how final works with constructors and instances
    private static final String DB_NAME = "YouCookDB";
    private static final Integer DB_VERSION = 1;

    //Here for easy reference
    //Should have done this for every field too.
    public static final String USERS_TABLE = "UsersTable";
    public static final String TAGS_TABLE = "TagsTable";
    public static final String RECIPES_TABLE = "RecipesTable";
    public static final String USER_FAVORITES_TABLE = "UserFavoritesTable";

    //Private because we will be using getDB_instance instead.
    //Context in this case will always be MainActivity
    private SQLiteDataAccess(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //Just one instance of the database
    //Because I read somewhere this would be safer??
    private static SQLiteDataAccess DB_instance;

    public static SQLiteDataAccess getDB_instance(Context context)
    {
        if (DB_instance == null)
            DB_instance = new SQLiteDataAccess(context);

        return DB_instance;
    }

    //Making this return a boolean to check if operation went through
    //Receives User, remember View to Model to Controller
    //In this case ViewModel -> Model -> Controller.  And backwards.
    public static boolean AddUser(SQLiteDatabase Database, IUsersModel User)
    {
        ContentValues cv = new ContentValues();

        //CHECK IF USER ALREADY EXISTS
        //TRIM VALUES, WRAP IN TRY CATCHES, CHECK EXCEPTIONS AND INPUT
        //Comes from google API so we wont need much

        /*
        SQLite creates a unique row id (rowid) automatically. This field is usually left out when you use "select * ...", but you can fetch this id by using "select rowid,* ...". Be aware that according to the SQLite documentation, they discourage the use of autoincrement.

        create table myTable ( code text, description text );
        insert into myTable values ( 'X', 'some descr.' );
        select rowid, * from myTable;
         */

        //Associative Array like PHP
        //Database Column + Value
        cv.put("Email", User.getEmail());
        cv.put("UserName", User.getUserName());
        cv.put("ImageURL", User.getPictureURL());

        //Insert into said database.

         /*
             nullColumnHack optional; may be null. SQL doesn't allow inserting a completely empty row without naming at least one column name.
             If your provided values is empty, no column names are known and an empty row can't be inserted.
             If not set to null, the nullColumnHack parameter provides the name of nullable column name to explicitly insert a NULL into in the case where your values is empty.
         */

        //Basically if we're trying to insert an empty row we need to fill this out.
        //insert will return -1 if failed.
        long insert = Database.insert(USERS_TABLE, null, cv);

        //if statement
        return insert == -1;
    }


    //This method is automatically called when the requests or inputs new data.
    @Override
    public void onCreate(SQLiteDatabase liteDatabase)
    {
        //USER TABLE
        String UserTableQuery = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (Email TEXT PRIMARY KEY , UserName TEXT, ImageURL TEXT);";
        //Execute
        liteDatabase.execSQL(UserTableQuery);

        //TAGS TABLE
        String TagsTableQuery = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TagText TEXT UNIQUE) WITHOUT ROWID;";
        //Execute
        liteDatabase.execSQL(TagsTableQuery);

        //RECIPES TABLE
        String RecipesTableQuery =
                "CREATE TABLE IF NOT EXISTS " + RECIPES_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, AuthorEmail TEXT, RecipeTitle TEXT, ImageURL TEXT,RecipeLongDescription TEXT, RecipeQuickDescription TEXT," +
                        "PrepTime INTEGER, CookTime INTEGER, DoneTime INTEGER, TagID INTEGER, FOREIGN KEY(AuthorEmail) REFERENCES " + USERS_TABLE + "(Email), FOREIGN KEY(TagID) REFERENCES " + TAGS_TABLE + "(ID)) WITHOUT ROWID;";
        //Execute
        liteDatabase.execSQL(RecipesTableQuery);

        //USER FAVORITES TABLE
        String UserFavoritesQuery = "CREATE TABLE IF NOT EXISTS " + USER_FAVORITES_TABLE + "(UserEmail TEXT, RecipeID Integer, PRIMARY KEY(UserEmail, RecipeID), " +
                "FOREIGN KEY(UserEmail) REFERENCES " + USERS_TABLE + "(Email), FOREIGN KEY(RecipeID) REFERENCES " + RECIPES_TABLE + "(ID));";
        //Execute
        liteDatabase.execSQL(UserFavoritesQuery);

    }

    //If new database version is available this will run, Updates "Upgrades" your Database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }


}