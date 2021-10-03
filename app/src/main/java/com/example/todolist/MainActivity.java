package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.todolist.adapter.TodoAdapter;
import com.example.todolist.data.TodoDB;
import com.example.todolist.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Connecting the UI key parts with the logic
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private CheckBox checkBox;

    //Instance of the DB and the Adapter for my recyclerView
    private TodoDB todoDB;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring ui in logic the simple way without data binding
        addButton = findViewById(R.id.add_todo_button);
        recyclerView = findViewById(R.id.todos_recycler_view);
        checkBox = findViewById(R.id.todo_checkbox);

        //We set a LayoutManager, instantiate the DB and set the adapter for recyclerView
        setupRecyclerViewData();

        //When we tap the add button, a dialog shows up
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We navigate to another view to add our Todos
                goAddNewTodo();
            }
        });

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox.isChecked()) {
//                    //TODO: Create modify and delete features for to dos
//                }
//            }
//        });

    }

    private void setupRecyclerViewData() {
        //Setting a layout manager to our recycler view, so it can show our todos
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //We create another db's instance to access data
        todoDB = new TodoDB(MainActivity.this);

        //Instantiate TodoAdapter and pass getAllTodos from parameter. Set adapter to our recyclerView
        todoAdapter = new TodoAdapter(todoDB.getAllTodos());
        recyclerView.setAdapter(todoAdapter);
    }

    private void goAddNewTodo() {
        Intent addTodoActivity = new Intent(getApplicationContext(), NewTodoActivity.class);
        startActivity(addTodoActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerViewData();
        //TODO: Esto no es lo correcto, creo, revisar
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}