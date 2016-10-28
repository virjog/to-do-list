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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        //for centering the action bar title
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        restoreTasks();

        taskList = (ListView) findViewById(R.id.mainListView);
        adapter = new TaskAdapter(this, tasks);
        taskList.setAdapter(adapter);

        //Event listener to handle deletion of tasks from list
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                saveTasks(v);
                return true;
            }
        });
    }

    /**
     * Method to add a Task object to the ArrayList, update the ListView, and save the contents
     * of the ArrayList to a file.
     * @param view
     */
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

    /**
     * Method to save tasks from ArrayList to a file named "tasks.txt"
     * @param view
     */
    public void saveTasks(View view) {
        String fileName = "tasks.txt";
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tasks);
            objectOutputStream.close();

            Toast.makeText(getBaseContext(), "Tasks saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to restore tasks to ArrayList from a file named "tasks.txt"
     */
    public void restoreTasks() {
        String fileName = "tasks.txt";
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            tasks = (ArrayList<Task>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}