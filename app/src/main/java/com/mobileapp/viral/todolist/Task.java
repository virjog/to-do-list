package com.mobileapp.viral.todolist;

import java.io.Serializable;

/**
 * Class for task object, which has a title and description, both as String variables,
 * and corresponding getter methods.
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