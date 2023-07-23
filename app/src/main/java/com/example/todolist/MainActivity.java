package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private CheckBox cb;
    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        cb = (CheckBox) findViewById(R.id.todoCheckBox);
        db = new DatabaseHandler(this);
        db.openDatabase();
        tasksRecyclerView = findViewById(R.id.taskRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, MainActivity.this) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            }
        };
        tasksRecyclerView.setAdapter(tasksAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
        fab = findViewById(R.id.fab);
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        fab.setOnClickListener(v
                -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG));
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}