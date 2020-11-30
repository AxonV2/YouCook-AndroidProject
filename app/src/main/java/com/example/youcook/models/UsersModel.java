package com.example.youcook.models;

import androidx.annotation.NonNull;

public class UsersModel implements IUsersModel
{
    String email;
    String userName;
    String pictureURL;

    //region GettersAndSetters
    //Getters
    @Override
    public String getUserName() { return userName; }
    @Override
    public String getEmail() { return email; }
    @Override
    public String getPictureURL() { return pictureURL; }

    //Setters
    @Override
    public void setUserName(String val) { userName = val; }
    @Override
    public void setEmail(String val) { email = val; }
    @Override
    public void setPictureURL(String val) { pictureURL = val; }
    //endregion


    public UsersModel(String EmailValue, String UserNameValue, String PictureURLValue)
    {
        this.email = EmailValue;
        this.userName = UserNameValue;
        this.pictureURL = PictureURLValue;
    }




}
