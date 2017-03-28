package com.web.exceptions;

import com.domain.Manager;


public class ManagerDeleteException extends Exception{
    
    private Manager manager;

    public ManagerDeleteException(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
    
    
}
