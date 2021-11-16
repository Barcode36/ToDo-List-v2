package com.adrict9.timeless.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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

    private Importance importance;

    private Date dateCreated;

    private Date dateOfDeadline;

    public Todo(String title, String subtitle, boolean done, Importance importance, Date dateCreated, Date dateOfDeadline) {
        this.title = title;
        this.subtitle = subtitle;
        this.done = done;
        this.importance = importance;
        this.dateCreated = dateCreated;
        this.dateOfDeadline = dateOfDeadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateOfDeadline() {
        return dateOfDeadline;
    }

    public void setDateOfDeadline(Date dateOfDeadline) {
        this.dateOfDeadline = dateOfDeadline;
    }
}
