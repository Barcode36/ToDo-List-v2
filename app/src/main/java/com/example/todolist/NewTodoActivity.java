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

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        editText = findViewById(R.id.editTextNewTodo);
        button = findViewById(R.id.addNewTodoButton);

        TodoDB db = new TodoDB(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().equals("")) {
                    Toast.makeText(NewTodoActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    long id = db.insertTodo(editText.getText().toString(), false);
                    cleanUpAndSetup();
                }
            }
        });

    }

    /**
     * Method to clean the editText and close the soft keyboard
     */
    private void cleanUpAndSetup() {
        editText.setText("");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}