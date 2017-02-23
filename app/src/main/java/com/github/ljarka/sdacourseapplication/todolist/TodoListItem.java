package com.github.ljarka.sdacourseapplication.todolist;

public class TodoListItem {

    private String text;
    private boolean isChecked;

    public TodoListItem(String text) {
        this.text = text;
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
