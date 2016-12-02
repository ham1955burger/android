package com.example.user.tableviewtest;

/**
 * Created by user on 11/28/16.
 */

import android.graphics.drawable.Drawable;


public class MainListItem {
    private Drawable image;
    private String title;
    private String subTitle;

    public void setImage(Drawable image) {
        this.image = image;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Drawable getImage() {
        return this.image;
    }
    public String getTitle() {
        return this.title;
    }
    public String getSubTitle() {
        return this.subTitle;
    }
}
