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

    Context context;

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

    //We create a method to store our todos on an ArrayList, and a cursor to navigate through the registers
    public ArrayList<Todo> getAllTodos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Todo> todoArrayList = new ArrayList<>();
        Todo todo = null;
        Cursor cursor = null;

        //SQL query to select everything from our database
        cursor = db.rawQuery("SELECT * FROM " + TODOS_TABLE_NAME, null);

        //With this method,the cursor moves to the first line, it starts getting all the db's data to
        //our objects until it has no next row. Do while, because I'm already sure that there is one row
        if (cursor.moveToFirst()) {
            do {
                todo = new Todo();
                todo.setTitle(cursor.getString(1 ));
                todo.setDone(cursor.getInt(2) > 0);
                todoArrayList.add(todo);
            } while (cursor.moveToNext());
        }

        //We close the cursor because It doesn't need to work anymore for now
        cursor.close();
        return todoArrayList;

    }

}
