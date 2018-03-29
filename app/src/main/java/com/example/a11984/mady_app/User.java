package com.example.a11984.mady_app;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String password;
    public String name_of_firm;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String password, String name_of_firm) {
        this.username = username;
        this.email = email;
        this.name_of_firm=name_of_firm;
        this.password=password;
    }

}
// [END blog_user_class]
