package com.adrict9.timeless.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.adrict9.timeless.data.TodoDAO;
import com.adrict9.timeless.model.Todo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    This class is where we check if Room DB is created and if not, we create it
 */

/*We make sure that Room identifies this as a DB and assign the entities (contained in Todo.class)
and also declare the DB version and turn exportSchema to false*/
@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class TodoRoomDatabase extends RoomDatabase {

    //We declare an instance of our TodoDAO, TodoRoomDatabase and the number of threads our executor
    //is going to use to execute CRUD tasks in the background thread
    public abstract TodoDAO todoDAO();
    public static final int THREADS_NUMBER = 4;

    private static volatile TodoRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecutor
            = Executors.newFixedThreadPool(THREADS_NUMBER);

    //We use this abstract class to create our Room DB if it doesn't exist, or if it does, return our db instance
    public static TodoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, "todo_table")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //We create a callback for RoomDatabase to be able to do CRUD operations
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriterExecutor.execute(() -> {
                TodoDAO todoDAO = INSTANCE.todoDAO();
                todoDAO.deleteAll();
            });
        }
    };

}
