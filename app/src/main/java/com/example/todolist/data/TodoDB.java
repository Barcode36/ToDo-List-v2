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
    public boolean insertTodo(String todoTitle, boolean todoDone) {

        //With this boolean we will check if the query execution was successful or not
        boolean updateSuccessful = true;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
        contentValues.put("title", todoTitle);
        contentValues.put("done", todoDone);

        db.insert(TODOS_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            Log.d("DB Exception", "Can't insert to do");
        }
        return updateSuccessful;
    }

    //This method allows us to insert to dos in our db
    public boolean editTodo(int id, String todoTitle, boolean todoDone) {

        //With this boolean we will check if the query execution was successful or not
        boolean updateSuccessful = true;

        //We make the DB readable to edit it
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        //This will update the data in our to dos
        try {
            //db.execSQL("UPDATE " + TODOS_TABLE_NAME + " SET title = '" + todoTitle + "', done = '" + todoDone + "' WHERE id='" + id + "' ");
            contentValues.put("id", id);
            contentValues.put("done", todoDone);
            contentValues.put("title", todoTitle);
            db.update(TODOS_TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
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
