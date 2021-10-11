package com.example.todolist.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.TodoDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class NewTodoActivity extends AppCompatActivity {

    //Declaring the UI in our activity
    private EditText todoEditText;
    private FloatingActionButton saveButton;
    private FloatingActionButton goBackButton;
    private FloatingActionButton setDoneButton;
    private TextView isDoneTextView;

    //Declaring the private varibles
    private boolean successfullyAdded;
    private boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoEditText = findViewById(R.id.editTodoEditText);
        saveButton = findViewById(R.id.addNewTodoButton);
        goBackButton = findViewById(R.id.goBackNewTodoButton);
        setDoneButton = findViewById(R.id.setDoneEditTodoButton);
        isDoneTextView = findViewById(R.id.isDoneTextView);

        // TODO: 11/10/2021 - Add a system to rotate between different hints
        todoEditText.setHint(R.string.add_new_todo_hint0);

        TodoDB db = new TodoDB(getApplicationContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoEditText.getText().length() > 0) {
                    successfullyAdded = db.insertTodo(todoEditText.getText().toString(), done);

                    //If the update was successful, we notify the user and move to the To Do's list. If it wasn't, we just notify and stay
                    if (successfullyAdded) {
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

        setDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (done) {
                    isDoneTextView.setText(R.string.done);
                    done = false;
                    setDoneButton.setImageResource(R.drawable.ic_baseline_done_24);
                } else {
                    isDoneTextView.setText(R.string.undone);
                    done = true;
                    setDoneButton.setImageResource(R.drawable.ic_baseline_clear_24);
                }
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