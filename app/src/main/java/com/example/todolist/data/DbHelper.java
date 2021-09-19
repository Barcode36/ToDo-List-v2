package com.example.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * In this class we will implement our CRUD system with the DB
 */

public class DbHelper extends SQLiteOpenHelper {

    //We declare our db's name and version
    public static final String DB_NAME = "todos.db";
    public static final int DB_VERSION = 1;

    //We declare our table's parameters
    public static final String TODOS_TABLE_NAME = "todo_table";
    public static final String TODO_ID = "id";
    public static final String TODO_TITLE = "title";
    public static final String TODO_DONE = "done";

    //This query creates a table suitable for our project
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TODOS_TABLE_NAME + "(" +
                    TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TODO_TITLE + " TEXT NOT NULL," +
                    TODO_DONE + " TEXT);";

    //This query will drop our table
    public static final String DROP_TABLE_QUERY =
            "DROP TABLE " + TODOS_TABLE_NAME;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //This method will create the db for us
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    //This method will drop and create another table for us when upgrading DB_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

}
