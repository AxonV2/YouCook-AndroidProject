package com.example.youcook.models;

public interface IUsersModel
{
    //Getters
    String getUserName();

    String getUserEmail();

    String getPictureURL();

    //Setters
    void setUserName(String val);

    void setUserEmail(String val);

    void setPictureURL(String val);
}
