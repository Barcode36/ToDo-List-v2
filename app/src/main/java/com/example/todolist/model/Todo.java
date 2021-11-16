package com.example.todolist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This is our Model for a to do item, this is the what every to do in our list is made of
 * It contains a title, subtitle, id and boolean to check if the task is completed or not
 */
@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String subtitle;

    private boolean done;

    private int priority;

    public Todo(@NonNull String title, String subtitle, boolean done, int priority) {
        this.title = title;
        this.subtitle = subtitle;
        this.done = done;
        this.priority = priority;
    }

    public Todo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() { return subtitle; }

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
