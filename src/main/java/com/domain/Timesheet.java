package com.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "timesheet")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee who;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private Integer hours;

    public Timesheet() {
    }

    public Timesheet(Employee who, Task task, Integer hours) {
        this.who = who;
        this.task = task;
        this.hours = hours;
    }

    public void setWho(Employee who) {
        this.who = who;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getWho() {
        return who;
    }

    public Task getTask() {
        return task;
    }

    public Integer getHours() {
        return hours;
    }

    /**
     * Manager can alter hours before closing task
     *
     * @param hours New amount of hours
     */
    public void alterHours(Integer hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Timesheet [who=" + who + ", task=" + task + ", hours=" + hours + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.who);
        hash = 79 * hash + Objects.hashCode(this.task);
        hash = 79 * hash + Objects.hashCode(this.hours);
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
        final Timesheet other = (Timesheet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.who, other.who)) {
            return false;
        }
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        if (!Objects.equals(this.hours, other.hours)) {
            return false;
        }
        return true;
    }

    
}
