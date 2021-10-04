package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.TodoDB;

public class NewTodoActivity extends AppCompatActivity {

    //Declaring the UI in our activity
    private EditText todoEditText;
    private Button saveButton;
    private Button goBackButton;

    //Declaring the private varibles
    private boolean successfullyCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoEditText = findViewById(R.id.editTodoEditText);
        saveButton = findViewById(R.id.addNewTodoButton);
        goBackButton = findViewById(R.id.goBackNewTodoButton);

        TodoDB db = new TodoDB(getApplicationContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoEditText.getText().length() > 0) {
                    successfullyCreated = db.insertTodo(todoEditText.getText().toString(), false);

                    //If the update was successful, we notify the user and move to the To Do's list. If it wasn't, we just notify and stay
                    if (successfullyCreated) {
                        Toast.makeText(NewTodoActivity.this, "Your ToDo was created :)", Toast.LENGTH_SHORT).show();
                        cleanUpAndSetup();
                        finish();
                    } else {
                        Toast.makeText(NewTodoActivity.this, "There was an error I need to solve :/", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewTodoActivity.this, "This ToDo will be empty :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Method to clean the editText and close the soft keyboard
     */
    private void cleanUpAndSetup() {
        todoEditText.setText("");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}