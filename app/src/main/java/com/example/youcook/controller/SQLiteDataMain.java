package com.example.youcook.controller;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataMain extends SQLiteOpenHelper
{
    //Final can only be assigned once,
    //Static remains the same no matter the instance
    //remember how final works with constructors and instances
    private static final String DB_NAME = "YouCookDB";
    private static final Integer DB_VERSION = 1;

    //Statics for easy reference
    //Should have done this for every field too.
    public static final String USERS_TABLE = "UsersTable";
    public static final String TAGS_TABLE = "TagsTable";
    public static final String RECIPES_TABLE = "RecipesTable";
    public static final String USER_FAVORITES_TABLE = "UserFavoritesTable";
    public static final String RECIPE_TAGS_TABLE = "RecipeTagsTable";

    //Private because we will be using getDB_instance instead.
    //Context in this case will always be MainActivity
    private SQLiteDataMain(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Instance of the database
    private static SQLiteDataMain DB_instance;

    //Getter for Instance
    public static SQLiteDataMain getDB_instance(Context context)
    {
        if (DB_instance == null)
            DB_instance = new SQLiteDataMain(context);

        return DB_instance;
    }

    //This method is automatically called when DB requests or inputs new data.
    @Override
    public void onCreate(SQLiteDatabase liteDatabase)
    {
        //E/SQLiteLog: (1) AUTOINCREMENT NOT ALLOWED ON WITHOUT ROWID TABLES

        //USER TABLE QUERY
        String UserTableQuery = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (Email TEXT PRIMARY KEY , UserName TEXT, ImageURL TEXT);";

        //TAGS TABLE QUERY
        String TagsTableQuery = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TagText TEXT UNIQUE);";

        //RECIPES TABLE QUERY
        String RecipesTableQuery =
                "CREATE TABLE IF NOT EXISTS " + RECIPES_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, AuthorEmail TEXT, RecipeTitle TEXT, ImageURL TEXT,RecipeLongDescription TEXT, RecipeQuickDescription TEXT," +
                        "PrepTime INTEGER, CookTime INTEGER, DoneTime INTEGER, FOREIGN KEY(AuthorEmail) REFERENCES " + USERS_TABLE + "(Email));";

        //USER FAVORITES TABLE QUERY
        String UserFavoritesQuery = "CREATE TABLE IF NOT EXISTS " + USER_FAVORITES_TABLE + "(UserEmail TEXT, RecipeID Integer, PRIMARY KEY(UserEmail, RecipeID), " +
                "FOREIGN KEY(UserEmail) REFERENCES " + USERS_TABLE + "(Email), FOREIGN KEY(RecipeID) REFERENCES " + RECIPES_TABLE + "(ID));";

        //RECIPE TAGS TABLE QUERY
        String RecipeTagsQuery = "CREATE TABLE IF NOT EXISTS " + RECIPE_TAGS_TABLE + "(RecipeID Integer, TagID Integer, PRIMARY KEY(RecipeID, TagID), " +
                "FOREIGN KEY(TagID) REFERENCES " + TAGS_TABLE + "(ID), FOREIGN KEY(RecipeID) REFERENCES " + RECIPES_TABLE + "(ID));";

        //Executes
        liteDatabase.execSQL(UserTableQuery);
        liteDatabase.execSQL(TagsTableQuery);
        liteDatabase.execSQL(RecipesTableQuery);
        liteDatabase.execSQL(UserFavoritesQuery);
        liteDatabase.execSQL(RecipeTagsQuery);
    }

    //If new database version is available this will run, Updates "Upgrades" your Database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}