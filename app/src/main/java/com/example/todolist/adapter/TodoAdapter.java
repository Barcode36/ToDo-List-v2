package com.example.todolist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.activities.DetailTodoActivity;
import com.example.todolist.R;
import com.example.todolist.model.Todo;

import java.util.ArrayList;

//We need this adapter to use the todos in our recyclerview
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder>{

    //Creating an ArrayList of to do objects
    ArrayList<Todo> listTodos;

    //We create a constructor that needs an arrayList of to dos for fill our recyclerView
    public TodoAdapter(ArrayList<Todo> listTodos) {
        this.listTodos = listTodos;
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_layout, null, false);
        return new TodoHolder(view);
    }

    //Here we assign the all the data from each position to where it has to be showed
    //(in element's list title or checkbox)
    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        holder.todoTitle.setText(listTodos.get(position).getTitle());
        if (listTodos.get(position).isDone()) {
            holder.todoCheck.setChecked(true);
        } else {
            holder.todoCheck.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return listTodos.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {

        //We declare the components that one To do has in our list and assign its id to their representation
        //in the logic
        private TextView todoTitle;
        private CheckBox todoCheck;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todoCheck = itemView.findViewById(R.id.todo_checkbox);

            //Handling the tap for the item in the list
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();

                    Intent intent = new Intent(context, DetailTodoActivity.class);
                    intent.putExtra("Id", listTodos.get(getAdapterPosition()).getId());

                    context.startActivity(intent);
                }
            });
        }

    }
}
