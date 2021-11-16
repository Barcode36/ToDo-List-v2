package com.example.todolist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.data.TodoRepository;

import java.util.List;

/**
 * We create a ViewModel for our app, that can interact with the repository, the activities and
 * conserve the data even if app configuration changes (screen orientation, etc...)
 */

public class TodoViewModel extends AndroidViewModel {

    //We instantiate our todos repository and also create a List with LiveData to observe changes
    public static TodoRepository todoRepository;
    public final LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allTodos = todoRepository.getAllTodos();
    }

    //We create a LiveData List to retrieve all our todos and expose them so other parts of the app
    //can interact with the data that the ViewModel is managing
    public LiveData<List<Todo>> getAllTodos() { return allTodos; }

    //With this method, we will insert a to do in our repository
    public static void insert(Todo todo) {todoRepository.insert(todo);}

    //With this method, we will delete a to do in our repository
    public static void delete(Todo todo) {todoRepository.delete(todo);}

    //With this method, we will update a to do in our repository
    public static void update(Todo todo) {todoRepository.update(todo);}

    //With this method, we will get a to do from our repository
    public LiveData<Todo> get(int id) {return todoRepository.get(id);}
}
