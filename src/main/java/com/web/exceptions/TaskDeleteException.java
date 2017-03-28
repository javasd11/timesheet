
package com.web.exceptions;

import com.domain.Task;


public class TaskDeleteException  extends Exception{
    private Task task;

    public TaskDeleteException(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
