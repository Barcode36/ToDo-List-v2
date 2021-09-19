package com.example.todolist.model;

import android.content.ContentValues;

/**
 * This is our Model for a todo item
 * It contains a title and a boolean to check if the task is completed or not
 */

public class Todo {

    private String title;
    private boolean done;

    public Todo(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    public Todo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
