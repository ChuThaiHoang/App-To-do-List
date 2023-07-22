package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRV;
    private ToDoAdapter adapter;
    private List<ToDoModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        modelList = new ArrayList<>();
        taskRV = (RecyclerView) findViewById(R.id.taskRecycleView);
        taskRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter(this) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }
        };
        taskRV.setAdapter(adapter);
        ToDoModel task = new ToDoModel();
        task.setTask("Đây là ghi chú mẫu");
        task.setId(1);
        task.setStatus(0);
        modelList.add(task);
        modelList.add(task);
        modelList.add(task);
        modelList.add(task);
        modelList.add(task);
        modelList.add(task);
        adapter.setTasks(modelList);
    }
}