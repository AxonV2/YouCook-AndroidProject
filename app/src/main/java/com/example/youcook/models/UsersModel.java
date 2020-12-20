package com.example.youcook.models;

public class UsersModel implements IUsersModel
{
    private String userEmail;
    private String userName;
    private String pictureURL;

    //region GettersAndSetters
    //Getters
    @Override
    public String getUserName() { return userName; }
    @Override
    public String getUserEmail() { return userEmail; }
    @Override
    public String getPictureURL() { return pictureURL; }

    //Setters
    @Override
    public void setUserName(String val) { userName = val; }
    @Override
    public void setUserEmail(String val) { userEmail = val; }
    @Override
    public void setPictureURL(String val) { pictureURL = val; }
    //endregion

    public UsersModel(String EmailValue, String UserNameValue, String PictureURLValue)
    {
        this.userEmail = EmailValue;
        this.userName = UserNameValue;
        this.pictureURL = PictureURLValue;
    }
}
