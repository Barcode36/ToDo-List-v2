package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.TodoDB;
import com.example.todolist.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailTodoActivity extends AppCompatActivity {

    //Declaring the UI in our activity
    private EditText todoText;
    private FloatingActionButton saveButton;
    private FloatingActionButton cancelButton;
    private FloatingActionButton deleteButton;
    private FloatingActionButton setDoneButton;
    private TextView isDoneText;

    //Instantiating a new To Do object and its id on the DB
    private Todo todo;

    //Declaring the private variables
    private int id;
    private boolean successfullyModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_todo);

        todoText = findViewById(R.id.editTodoEditText);
        saveButton = findViewById(R.id.saveEditTodoButton);
        cancelButton = findViewById(R.id.goBackEditTodoButton);
        deleteButton = findViewById(R.id.deleteEditTodoButton);
        setDoneButton = findViewById(R.id.setDoneEditTodoButton);
        isDoneText = findViewById(R.id.isDoneTextView);

        //We check if savedInstanceState already contains the info we need to fill our editText
        if (savedInstanceState == null) {
            //Then, we wanna know if the To Do id is correctly stored in the data we passed to this activity
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                //And if they are not null, we get the Id we added to the intent extras'
                id = extras.getInt("id");
            } else {
                id = Integer.parseInt(null);
            }
        } else {
            //If we already have the Id loaded, we just put it on our variable
            id = (int) savedInstanceState.getSerializable("Id");
        }

        //We instantiate our db and get the info for the desired item (with its id to get it from the db)
        TodoDB db = new TodoDB(this);
        todo = db.getTodo(id);

        //We check if the item is null and if not, we get the data from it to our activity UI
        if (todo != null) {
            todoText.setText(todo.getTitle());
        }

        //We check if the To do is done or not to show a different text about the task state (done / not done)
        if (todo.isDone()) {
            isDoneText.setText("Undone");
            setDoneButton.setImageResource(R.drawable.ic_baseline_clear_24);
        } else {
            isDoneText.setText("Set done");
            setDoneButton.setImageResource(R.drawable.ic_baseline_done_24);
        }

        //Handling tap in the "Save" button and calling the method we created to update the to do
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if there's text in the editText where the user modifies the To Do
                if (todoText.getText().length() > 0) {
                    successfullyModified = db.editTodo(id, todoText.getText().toString(), db.getTodo(id).isDone());

                    //If the update was successful, we notify the user and move to the To Do's list. If it wasn't, we just notify and stay
                    if (successfullyModified) {
                        Toast.makeText(DetailTodoActivity.this, "Your ToDo was saved! :)", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailTodoActivity.this, "There was an error I need to solve :/", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DetailTodoActivity.this, "This ToDo will be empty now :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Handling tap in the "Go back" Button to get the user to the MainActivity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Handling tap on the "Delete" Button to delete the task and move the user to the main screen
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successfullyModified = db.deleteTodo(id);

                //If it was deleted successfully, we notify the user and move to the To Do's list. If it wasn't, we just notify and stay
                if (successfullyModified) {
                    Toast.makeText(DetailTodoActivity.this, "Your ToDo was deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTodoActivity.this, "There was an error I need to solve :/", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

        setDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean todoState = db.getTodo(id).isDone();

                 successfullyModified = db.editTodo(id, db.getTodo(id).getTitle(), !todoState);

                //If it was updated successfully, we notify the user and go back home
                if (successfullyModified) {
                    Toast.makeText(DetailTodoActivity.this, "Your ToDo was modified!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTodoActivity.this, "There was an error I need to solve :/", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}