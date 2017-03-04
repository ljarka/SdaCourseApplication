package com.github.ljarka.sdacourseapplication.books;

import android.support.annotation.DrawableRes;

public class Book {

    private int id;
    private boolean isRead;
    @DrawableRes
    private int imageResourceId;
    private String title;

    public Book(int id, @DrawableRes int imageResourceId,
            String title) {
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @DrawableRes
    public int getImageResourceId() {
        return imageResourceId;
    }
}
