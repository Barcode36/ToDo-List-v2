package com.example.todolist.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todolist.model.Todo;
import com.example.todolist.util.TodoRoomDatabase;

import java.util.List;

/**
 * This repository class allows us to have another level of abstraction and multiple data sources
 * But the main function of this, is being the single source of "truth" for our app
 */

public class TodoRepository {
    private TodoDAO todoDAO;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        todoDAO = db.todoDAO();

        allTodos = todoDAO.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {return allTodos;}

    //With this method we insert a new to do an use databaseWriterExecutor to execute the insert on a background thread
    public void insert(Todo todo) {
        TodoRoomDatabase.databaseWriterExecutor.execute(() -> {
            todoDAO.insert(todo);
        });
    }

    //With this method we call the delete command from TodoDAO where we use Room to make CRUD in our DB
    public void delete(Todo todo) {
        TodoRoomDatabase.databaseWriterExecutor.execute(() -> {
            todoDAO.delete(todo);
        });
    }

    //With this method we call the update command from TodoDAO
    public void update(Todo todo) {
        TodoRoomDatabase.databaseWriterExecutor.execute(() -> {
            todoDAO.update(todo);
        });
    }

    //With this method we call the get to do command from TodoDAO
    public LiveData<Todo> get(int id) {
        return todoDAO.get(id);
    }

}
