package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.model.Todo;
import com.example.todolist.model.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DetailTodoActivity extends AppCompatActivity {

    //Declaring variables for our activity
    private int todoId;
    private boolean done;

    //Instantiating our viewModel
    private TodoViewModel todoViewModel;

    //Declaring the UI in our activity
    private EditText titleEditText;
    private EditText subtitleEditText;
    private FloatingActionButton saveButton;
    private FloatingActionButton goBackButton;
    private FloatingActionButton deleteButton;
    private FloatingActionButton setDoneButton;
    private TextView isDoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_todo);

        titleEditText = findViewById(R.id.titleEditTodo);
        subtitleEditText = findViewById(R.id.subtitleEditTodo);

        saveButton = findViewById(R.id.saveEditTodoButton);
        goBackButton = findViewById(R.id.goBackEditTodoButton);
        deleteButton = findViewById(R.id.deleteEditTodoButton);
        setDoneButton = findViewById(R.id.setDoneEditTodoButton);
        isDoneTextView = findViewById(R.id.isDoneEditTodoTextView);

        //We instantiate thew ViewModelProvider using AndroidViewModelFactory to assign it to our viewModel object
        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailTodoActivity.this
                .getApplication())
                .create(TodoViewModel.class);

        //Receiving info that comes with the intent to identify which to do we are selecting with the viewModel
        if (getIntent().hasExtra(MainActivity.TODO_ID)) {
            todoId = getIntent().getIntExtra(MainActivity.TODO_ID, 0);

            todoViewModel.get(todoId).observe(this, todo -> {
                if (todo != null) {
                    titleEditText.setText(todo.getTitle());
                    subtitleEditText.setText(todo.getSubtitle());
                    if (todo.isDone()) {
                        done = true;
                        isDoneTextView.setText(R.string.undo);
                        setDoneButton.setImageResource(R.drawable.ic_baseline_clear_24);
                    } else {
                        done = false;
                        isDoneTextView.setText(R.string.done);
                        setDoneButton.setImageResource(R.drawable.ic_baseline_done_24);
                    }
                }
            });
        }

        //Handling tap in the "Go back" Button to get the user to the MainActivity
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Handling tap in the "Save" Button to save our to do
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyTodoData("edit");
            }
        });

        //Handling tap in the "Delete" Button to delete our to dos
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyTodoData("delete");
            }
        });

        //Handling tap in the "Done/Undone" Button to set the state of our to do
        setDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (done) {
                    done = false;
                    isDoneTextView.setText(R.string.done);
                    setDoneButton.setImageResource(R.drawable.ic_baseline_done_24);
                } else {
                    done = true;
                    isDoneTextView.setText(R.string.undo);
                    setDoneButton.setImageResource(R.drawable.ic_baseline_clear_24);
                }
            }
        });

    }

    private void modifyTodoData(String action) {
        String title = titleEditText.getText().toString();
        String subtitle = subtitleEditText.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(subtitle)) {
            Snackbar.make(titleEditText, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            Todo todo = new Todo();
            todo.setId(todoId);
            todo.setTitle(title);
            todo.setSubtitle(subtitle);
            todo.setDone(done);
            if (action.equals("delete"))
                todoViewModel.delete(todo);
            else if (action.equals("edit"))
                todoViewModel.update(todo);
            finish();
        }
    }

}