package com.github.ljarka.sdacourseapplication.todolist;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoListItem implements Parcelable {

    private String text;
    private boolean isChecked;

    public TodoListItem(String text) {
        this.text = text;
    }

    protected TodoListItem(Parcel in) {
        text = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<TodoListItem> CREATOR = new Creator<TodoListItem>() {

        @Override
        public TodoListItem createFromParcel(Parcel in) {
            return new TodoListItem(in);
        }

        @Override
        public TodoListItem[] newArray(int size) {
            return new TodoListItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
