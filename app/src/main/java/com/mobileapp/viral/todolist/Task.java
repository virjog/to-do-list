package com.mobileapp.viral.todolist;

import java.io.Serializable;

/**
 * Created by viral on 10/3/16.
 */

public class Task implements Serializable {
    private String title;
    private String description;

    public Task(String t, String d) {
        this.title = t;
        this.description = d;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
