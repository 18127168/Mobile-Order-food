package com.example.masterchef;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String user,pass;
    public User() {
        this.user = "";
        this.pass = "";
    }
    public User(String user,String pass){
        this.user = user;
        this.pass = pass;
    }
}