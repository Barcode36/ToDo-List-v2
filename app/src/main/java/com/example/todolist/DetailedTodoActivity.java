package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist.data.TodoDB;
import com.example.todolist.model.Todo;

public class DetailedTodoActivity extends AppCompatActivity {

    //Declaring the UI in our activity
    private EditText todoText;
    private Button saveButton;
    private Button goBackButton;

    //Instantiating a new To Do object and its id on the DB
    Todo todo;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_todo);

        todoText = findViewById(R.id.editTodoEditText);
        saveButton = findViewById(R.id.saveEditTodoButton);
        goBackButton = findViewById(R.id.goBackEditTodoButton);

        //We check if savedInstanceState already contains the info we need to fill our editText
        if (savedInstanceState == null) {
            //Then, we wanna know if the To Do id is correctly stored in the data we passed to this activity
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                //And if they are not null, we get the Id we added to the intent extras'
                id = extras.getInt("Id");
            } else {
                id = Integer.parseInt(null);
            }
        } else {
            //If we already have the Id loaded, we just put it on our variable
            id = (int) savedInstanceState.getSerializable("Id");
        }

        //We instantiate our db and get the info for the desired item (with its id to get it from the db)
        TodoDB todoDB = new TodoDB(this);
        todo = todoDB.getTodo(id);

        //We check if the item is null and if not, we get the data from it to our activity UI
        if (todo != null) {
            todoText.setText(todo.getTitle());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save in the DB
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}