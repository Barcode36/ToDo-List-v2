package com.adrict9.timeless.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.adrict9.timeless.model.Todo;

import java.util.List;

/**
 * We create a Data Access Object Interface for our app, which is where we access to our data with
 * one more level of abstraction than with plain SQL. We put annotations so Room knows that this is part of it
 */

@Dao
public interface TodoDAO {
    //CRUD functions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    LiveData <List<Todo>> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE todo_table.id == :id")
    LiveData<Todo> get(int id);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

}
