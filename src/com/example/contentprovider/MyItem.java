package com.example.contentprovider;

public class MyItem {

    private String mTitle;
    private String mDescription;
    private int mLevel;
    private String mIdentifier;
    private String mDateTime;

    public MyItem() {
        mTitle = "";
        mDescription = "";
        mDateTime = "";
        mLevel = 0;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescrition() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int Level) {
        mLevel = Level;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(String Identifier) {
        mIdentifier = Identifier;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String datetime) {
        mDateTime = datetime;
    }
}