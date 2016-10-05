package com.mobileapp.viral.todolist;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileapp.viral.todolist.Task;
import com.mobileapp.viral.todolist.TaskAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskAdapter adapter;
    private ListView taskList;
    private EditText newTaskTitle;
    private EditText newTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        tasks = new ArrayList<Task>();
        taskList = (ListView) findViewById(R.id.mainListView);
        adapter = new TaskAdapter(this, tasks);
        taskList.setAdapter(adapter);

        restoreTasks();

        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    public void onAddTask(View view) {
        newTaskTitle = (EditText) findViewById(R.id.titleTextInput);
        newTaskDescription = (EditText) findViewById(R.id.descriptionTextInput);

        Task task = new Task(newTaskTitle.getText().toString(), newTaskDescription.getText().toString());
        tasks.add(task);
        adapter.notifyDataSetChanged();

        saveTasks(view);

        newTaskTitle.setText("");
        newTaskDescription.setText("");
    }

    public void saveTasks(View view) {
        String fileName = "listOfTasks.txt";
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();

            Toast.makeText(getBaseContext(), "Tasks saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreTasks() {
        String fileName = "listOfTasks.txt";
        try {
            FileInputStream fis = getApplicationContext().openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (ArrayList<Task>) ois.readObject();
            ois.close();

            Toast.makeText(getBaseContext(), "Tasks restored successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}






















