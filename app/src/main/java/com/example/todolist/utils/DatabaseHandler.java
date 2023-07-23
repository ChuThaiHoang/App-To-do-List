package com.example.todolist.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String Name = "ToDoListDB";
    private static final String ToDoTable = "todo";
    private static final String ID = "id";
    private static final String Task = "task";
    private static final String Status = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + ToDoTable + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Task + " TEXT, "
            + Status + " INTEGER)";
    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ToDoTable);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void InsertTask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(Task, task.getTask());
        cv.put(Status, 0);
        db.insert(ToDoTable, null, cv);
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTask() {
        List<ToDoModel> tasklist = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(ToDoTable, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(Task)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(Status)));
                        tasklist.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return tasklist;
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(Status, status);
        db.update(ToDoTable, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(Task, task);
        db.update(ToDoTable, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void getTaskById(int id) {
        db.execSQL("select task where " + id + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(ToDoTable, ID + "= ?", new String[]{String.valueOf(id)});
    }
}
