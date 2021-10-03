package com.example.todolist.model;

import android.content.ContentValues;

/**
 * This is our Model for a to do item
 * It contains a title and a boolean to check if the task is completed or not
 */

public class Todo {

    private String title;
    private boolean done;
    private int id;

    public Todo(String title, boolean done, int id) {
        this.title = title;
        this.done = done;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
