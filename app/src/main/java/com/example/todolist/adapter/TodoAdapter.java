package com.example.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.Todo;

import java.util.List;

//We need this adapter to have the to dos in our recyclerview
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    //We create an instance of the interface to pass it to the constructor and require it when using it
    private OnTodoClickListener todoClickListener;
    //Creating an ArrayList of to do objects and creating an instance of Context for using it
    private List<Todo> listTodos;
    final private Context context;

    //We create a constructor that needs an arrayList of to dos to fill the recyclerView
    public TodoAdapter(List<Todo> listTodos, Context context, OnTodoClickListener onTodoClickListener) {
        this.listTodos = listTodos;
        this.context = context;
        this.todoClickListener = onTodoClickListener;
    }

    //We inflate the view to parse the XML to Java objects so we can work with the recyclerView
    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_layout, null, false);
        return new TodoHolder(view, todoClickListener);
    }

    //Here we assign the all the data from each to do to each element of the recyclerView through the holder
    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo todo = listTodos.get(position);

        holder.todoTitle.setText(todo.getTitle());
        holder.todoSubtitle.setText(todo.getSubtitle());
        holder.todoCheck.setChecked(todo.isDone());
    }

    @Override
    public int getItemCount() {
        return listTodos.size();
    }

    //We implement the OnClickListener because each element should do something when it's clicked and we also pass it to the constructor
    //so it will be required when using this adapter, and also it makes it possible to help us retrieve the right info
    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //We create an instance of the interface we created
        OnTodoClickListener onTodoClickListener;
        //We declare the UI parts of each element in the recyclerView
        private TextView todoTitle;
        private TextView todoSubtitle;
        private CheckBox todoCheck;

        public TodoHolder(@NonNull View itemView, OnTodoClickListener onTodoClickListener) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todoSubtitle = itemView.findViewById(R.id.todo_subtitle);
            todoCheck = itemView.findViewById(R.id.todo_checkbox);

            todoCheck.setEnabled(false);

            //We make the clickListener be equal to the instance of the interface we created
            //and we also attach each element in the RecyclerView to an OnClickListener
            this.onTodoClickListener = onTodoClickListener;
            itemView.setOnClickListener(this);

        }

        //With this method required for implementing View.OnClickListener, we get the position of the selected element
        @Override
        public void onClick(View v) {
            onTodoClickListener.onTodoClicked(getAdapterPosition());
        }
    }

    //We implement an interface to be able to get the position of the item the user is selecting
    public interface OnTodoClickListener {
        void onTodoClicked(int position);
    }

}
