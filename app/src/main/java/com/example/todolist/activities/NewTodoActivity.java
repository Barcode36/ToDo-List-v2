package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.model.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewTodoActivity extends AppCompatActivity {

    //Declaring private variables for our activity
    public static final String TITLE_REPLY = "title_reply";
    public static final String SUBTITLE_REPLY = "subtitle_reply";
    public static final String DONE_REPLY = "done_reply";
    public static final String PRIORITY_REPLY = "priority_reply";
    private boolean done = false;
    private int priority = 0;

    //Instantiating our viewModel
    private TodoViewModel todoViewModel;

    //Declaring the UI in our activity
    private EditText titleEditText;
    private EditText subtitleEditText;
    private FloatingActionButton saveButton;
    private FloatingActionButton goBackButton;
    private FloatingActionButton setDoneButton;
    private TextView isDoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        titleEditText = findViewById(R.id.titleAddTodo);
        subtitleEditText = findViewById(R.id.subtitleAddTodo);

        saveButton = findViewById(R.id.saveAddTodoButton);
        goBackButton = findViewById(R.id.goBackAddTodoButton);
        setDoneButton = findViewById(R.id.setDoneAddTodoButton);
        isDoneTextView = findViewById(R.id.isDoneAddTodoButton);

        // TODO: 11/10/2021 - Add a system to rotate between different hints
        titleEditText.setHint(R.string.add_new_todo_hint0);

        //We instantiate thew ViewModelProvider using AndroidViewModelFactory to assign it to our viewModel object
        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTodoActivity.this
                .getApplication())
                .create(TodoViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                if (!TextUtils.isEmpty(titleEditText.getText().toString()) && !TextUtils.isEmpty(subtitleEditText.getText().toString())) {

                    String title = titleEditText.getText().toString();
                    String subtitle = subtitleEditText.getText().toString();

                    replyIntent.putExtra(TITLE_REPLY, title);
                    replyIntent.putExtra(SUBTITLE_REPLY, subtitle);
                    replyIntent.putExtra(DONE_REPLY, done);
                    replyIntent.putExtra(PRIORITY_REPLY, priority);
                    setResult(RESULT_OK, replyIntent);

                } else {
                    Toast.makeText(NewTodoActivity.this, R.string.full_empty, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
                cleanUpAndSetup();
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
                    isDoneTextView.setText(R.string.undo);
                    done = true;
                    setDoneButton.setImageResource(R.drawable.ic_baseline_clear_24);
                }
            }
        });

    }

    /**
     * Method to clean the text fields and close the soft keyboard
     */
    private void cleanUpAndSetup() {
        titleEditText.setText("");
        subtitleEditText.setText("");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}