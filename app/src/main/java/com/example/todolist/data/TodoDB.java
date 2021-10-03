package com.example.todolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todolist.model.Todo;

import java.util.ArrayList;

public class TodoDB extends DbHelper {

    private Context context;

    //Creating a constructor for our class, extending DbHelper to use its data
    public TodoDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //This method allows us to insert todos in our db
    public long insertTodo(String todoTitle, boolean todoDone) {
        long id = 0;

        try {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", todoTitle);
        values.put("done", todoDone);

        id = db.insert(TODOS_TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.d("DB Exception", "Can't insert todo");
        }
        return id;
    }

    //This method allows us to insert todos in our db
    public boolean editTodo(int id, String todoTitle, boolean todoDone) {

        //With this boolean we will check if the query execution was successful or not
        boolean updateSuccessful;

        //We make the DB readable to edit it
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //This SQL query will try update the title, and if it is done or not in the DB
        try {
            /*TODO: Change this to another method, because when I introduce "I'll" I break the SQL
            https://stackoverflow.com/questions/24589411/special-characters-in-sqlitedatabase-android
             */
            db.execSQL("UPDATE " + TODOS_TABLE_NAME + " SET title = '" + todoTitle + "', done = '" + todoDone + "' WHERE id='" + id + "' ");
            updateSuccessful = true;
        } catch (Exception e) {
            e.toString();
            updateSuccessful = false;
        } finally {
            //We close the connection with the db if the to do was updated successfully or not
            db.close();
        }
        return updateSuccessful;
    }

    //We create a method to store our todos on an ArrayList, and a cursor to navigate through the registers
    public ArrayList<Todo> getAllTodos() {

        //Instantiating our dbHelper & making our db writable
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Todo> todoArrayList = new ArrayList<>();
        Todo todo;
        Cursor cursor;

        //SQL query to select everything from our database
        cursor = db.rawQuery("SELECT * FROM " + TODOS_TABLE_NAME, null);

        //With this method,the cursor moves to the first line, it starts getting all the db's data to
        //our objects until it has no next row. Do while, because I'm already sure that there is one row
        if (cursor.moveToFirst()) {
            do {
                todo = new Todo();
                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1));
                todo.setDone(cursor.getInt(2) > 0);
                todoArrayList.add(todo);
            } while (cursor.moveToNext());
        }

        //We close the cursor because It doesn't need to work anymore for now
        cursor.close();

        return todoArrayList;
    }

    //We create a method to get the info of one of our To Do items
    public Todo getTodo(int id) {

        //Instantiating our dbHelper & making our db writable
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Todo todo = null;
        Cursor cursor;

        //SQL query to select the to do item we selected from our database
        cursor = db.rawQuery("SELECT * FROM " + TODOS_TABLE_NAME + " WHERE id = " + id + " LIMIT 1", null);

        //With this method,the cursor moves to the first line, it starts getting all the db's data to
        //our objects until it has no next row. Do while, because I'm already sure that there is one row
        if (cursor.moveToFirst()) {
                todo = new Todo();
                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1 ));
                todo.setDone(cursor.getInt(2) > 0);
        }

        //We close the cursor because It doesn't need to work anymore for now
        cursor.close();

        return todo;
    }

}
