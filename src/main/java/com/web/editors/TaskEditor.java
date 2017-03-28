
package com.web.editors;

import java.beans.PropertyEditorSupport;
import com.domain.Task;
import com.service.dao.TaskDao;


public class TaskEditor extends PropertyEditorSupport{
    private TaskDao taskDao;

    public TaskEditor(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        long id = Long.parseLong(text);
        Task task = taskDao.find(id);
        setValue(task);
    }
    
    

}
