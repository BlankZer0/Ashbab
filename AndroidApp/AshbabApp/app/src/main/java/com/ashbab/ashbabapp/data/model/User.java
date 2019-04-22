package com.ashbab.ashbabapp.data.model;

public class User
{
    private String uID;
    private String userName;
    private String userEmail;
    private String userPhotoUrl;
    private String userAddress;

    public User() {}

    public User(String uID, String userName, String userEmail, String userPhotoUrl)
    {
        this.userName = userName;
        this.uID = uID;
        this.userEmail = userEmail;
        this.userPhotoUrl = userPhotoUrl;
    }

    public User(String uID, String userName, String userEmail, String userPhotoUrl, String userAddress)
    {
        this.uID = uID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhotoUrl = userPhotoUrl;
        this.userAddress = userAddress;
    }

    public String getuID() { return uID; }

    public String getUserName() { return userName; }

    public String getUserEmail() { return userEmail; }

    public String getUserPhotoUrl() { return userPhotoUrl; }

    public String getUserAddress() { return userAddress; }

    public void setuID(String uID) { this.uID = uID; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public void setUserPhotoUrl(String userPhotoUrl) { this.userPhotoUrl = userPhotoUrl; }

    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }

    @Override
    public String toString()
    {
        return "User{" +
                "userName='" + userName + '\'' +
                ", uID='" + uID + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }
}
