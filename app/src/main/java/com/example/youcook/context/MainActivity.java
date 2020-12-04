package com.example.youcook.context;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.youcook.R;
import com.example.youcook.controller.SQLiteDataMain;
import com.example.youcook.controller.SQLiteDataHelper;
import com.example.youcook.ui.create.CreateViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    //Search item public fragment view.
    static public MenuItem searchItem;
    //Database Instance
    static public SQLiteDataMain YouCookDatabase;

    //Image to be set by user for create recipe fragment
    static public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Database Instance Creation
        //this = MainActivity Context
        YouCookDatabase = SQLiteDataMain.getDB_instance(this);
        //to view DB go to VIEW -> TOOL WINDOW -> DEVICE FILE EXPLORER
        //then DATA -> DATA -> APP FOLDER (YOUCOOK) -> DATABASES -> Select and export
            SQLiteDataHelper.GiveValues();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Here we pass in each created navigation
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_recipes, R.id.nav_create, R.id.nav_favorites).setDrawerLayout(drawer).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //SearchBar Reference, id given in menu -> main.xml
        //Made public so we can call it in RecipesFragment
        searchItem = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    //RECIPE IMAGE INSERT
    public static final int GET_FROM_GALLERY = 0;

    public void addImage(View view)
    {
        //Send to gallery
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        imageView = view.findViewById(R.id.createRecipeImage);
    }

    //After getting image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        //If it got the picture normally
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK)
        {
            //Get it's data
            Uri selectedImage = data.getData();

            //Set bitmap
            Bitmap bitmap = null;
            try {

                //Give bitmap data
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                CreateViewModel.setCreatedRecipeImage(bitmap);
                //Log.d("Tag", "onClick：" + bitmap);

                //Load bitmap into create imageview
                Glide.with(MainActivity.this).load(bitmap).apply(new RequestOptions().override(1000, 600)).centerCrop().into(imageView);
                //imageView.setImageBitmap(bitmap);

            } catch (IOException e)
            {
                Log.d("Tag", "ImageRetrieval Exception：" + e);
                e.printStackTrace();
            }
        }
    }
}