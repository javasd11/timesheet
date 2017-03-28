package com.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_employee", joinColumns = {
        @JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<Employee> assignedEmployees = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    private String description;
    boolean completed;

    public Task() {
    }

    public Task(String description, Manager manager, Employee... employees) {
        this.description = description;
        this.manager = manager;
        assignedEmployees.addAll(Arrays.asList(employees));
        completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDescription() {
        return description;
    }

    public void setAssignedEmployees(List<Employee> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void addEmployee(Employee e) {
        assignedEmployees.add(e);
    }

    public void removeEmployee(Employee e) {
        assignedEmployees.remove(e);
    }

    public void completeTask() {
        completed = true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.assignedEmployees);
        hash = 29 * hash + Objects.hashCode(this.manager);
        hash = 29 * hash + (this.completed ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;

        if (!Objects.equals(this.manager, other.manager)) {
            return false;
        }
        if (this.completed != other.completed) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", assignedEmployees=" + assignedEmployees
                + ", manager=" + manager + ", description=" + description + ", completed=" + completed + "}";
    }

}
