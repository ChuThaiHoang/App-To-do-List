package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView taskRV;
    private ToDoAdapter adapter;
    private List<ToDoModel> modelList;
    private DatabaseHandler db;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        db = new DatabaseHandler(this);
        db.openDatabase();
//        modelList = new ArrayList<>();
        taskRV = (RecyclerView) findViewById(R.id.taskRecycleView);
        taskRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter(db, this) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }
        };
        taskRV.setAdapter(adapter);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        modelList = db.getAllTask();
        Collections.reverse(modelList);
        adapter.setTasks(modelList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        modelList = db.getAllTask();
        Collections.reverse(modelList);
        adapter.setTasks(modelList);
        adapter.notifyDataSetChanged();
    }
}