package com.example.todolist.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.example.todolist.R;
import com.example.todolist.adapter.TodoAdapter;
import com.example.todolist.model.Todo;
import com.example.todolist.model.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoClickListener {

    //Declaring variables that I need to move to the activities
    public static final int NEW_TODO_REQUEST_CODE = 1;
    public static final int DETAIL_TODO_REQUEST_CODE = 1;
    public static final String TODO_ID = "todo_id";

    //Declaring a list with LiveData, to be able to observe it
    private LiveData<List<Todo>> todoList;

    //Connecting the UI key parts with the logic
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private CheckBox checkBox;

    //Instance of the ViewModel and the Adapter for the recyclerView
    private TodoViewModel todoViewModel;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring ui in logic the simple way without data binding
        addButton = findViewById(R.id.add_todo_button);
        checkBox = findViewById(R.id.todo_checkbox);
        recyclerView = findViewById(R.id.todos_recycler_view);

        //We instantiate thew ViewModelProvider using AndroidViewModelFactory to assign it to our viewModel object
        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
            .getApplication())
            .create(TodoViewModel.class);

        //We observe the content of our viewModel with LiveData and update the recyclerView if it changes
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                //With this, we optimize a little bit the recycler's performance
                recyclerView.setHasFixedSize(true);
                //Setting a layout manager to our recycler view, to manage how elements are organized
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                //We assign the required parameters to our adapter
                todoAdapter = new TodoAdapter(todos, MainActivity.this, MainActivity.this);
                //We set the recyclerView's adapter to todoAdapter to "configure" it with the code we wrote
                recyclerView.setAdapter(todoAdapter);
            }
        });



        //When we tap the add button, a dialog shows up
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We navigate to another view to add our Todos
                goAddNewTodo();
            }
        });

    }

    /**
     * Depending on the resultCode we get, we will do one or other thing
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TODO_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NewTodoActivity.TITLE_REPLY);
            String subtitle = data.getStringExtra(NewTodoActivity.SUBTITLE_REPLY);
            boolean done = data.getBooleanExtra(NewTodoActivity.DONE_REPLY, false);

            assert title != null;
            Todo todo = new Todo(title, subtitle, done);

            todoViewModel.insert(todo);
        }
    }

    /**
     * Move user to the new activity waiting for the successful response
     */
    private void goAddNewTodo() {
        Intent goNewTodoActivity = new Intent(getApplicationContext(), NewTodoActivity.class);
        startActivityForResult(goNewTodoActivity,NEW_TODO_REQUEST_CODE);
    }

    /**
     * Move user to the detail activity with the id of the to do selected
     */
    private void goDetailTodo(int position) {
        Todo todo = todoViewModel.getAllTodos().getValue().get(position);
        Intent goDetailTodoActivity = new Intent(getApplicationContext(), DetailTodoActivity.class);
        goDetailTodoActivity.putExtra(TODO_ID, todo.getId());
        startActivityForResult(goDetailTodoActivity, DETAIL_TODO_REQUEST_CODE);
    }

    @Override
    public void onTodoClicked(int position) {
        goDetailTodo(position);
    }
}