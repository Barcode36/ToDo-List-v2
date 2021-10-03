package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.data.TodoDB;

public class NewTodoActivity extends AppCompatActivity {

    private EditText toDoEditText;
    private Button saveButton;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        toDoEditText = findViewById(R.id.editTodoEditText);
        saveButton = findViewById(R.id.addNewTodoButton);
        goBackButton = findViewById(R.id.goBackNewTodoButton);

        TodoDB db = new TodoDB(getApplicationContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toDoEditText.getText().length() == 0) {
                    Toast.makeText(getBaseContext(), "Nothing to save :(", Toast.LENGTH_SHORT).show();
                } else {
                    long id = db.insertTodo(toDoEditText.getText().toString(), false);
                    cleanUpAndSetup();
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
        toDoEditText.setText("");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}