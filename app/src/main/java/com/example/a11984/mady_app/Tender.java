package com.example.a11984.mady_app;

/**
 * Created by 11984 on 26.03.2018.
 */

public class Tender {
    public String details;
    public String type;
    public String Name;
    public Tender() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Tender( String details,String type,String Name) {
        this.details = details;
        this.type=type;
        this.Name=Name;
}}
