
package com.web.commands;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import com.domain.Timesheet;



public class TimesheetCommand {
    
    @NotNull
    @Range(min=1, message="Hoursggggg  must be 1 or greater")
    private Integer hours;
    private Timesheet timesheet;

    public TimesheetCommand(Integer hours, Timesheet timesheet) {
        this.hours = hours;
        this.timesheet = timesheet;
    }

    public TimesheetCommand(Timesheet timesheet) {
        this.timesheet = timesheet;
        hours = timesheet.getHours();
    }
   
    public TimesheetCommand() {
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
    
    @Override
    public boolean equals(Object o){
    
        if(this == o){
            return true;
        }
        if (o==null || getClass()!=o.getClass()){
            return false;
        }
        TimesheetCommand that = (TimesheetCommand)o;
        
        if(hours!=null ? !hours.equals(that.hours) : that.hours !=null){
            return false;
        }
        if(timesheet!=null ? !timesheet.equals(that.timesheet) : that.timesheet!=null){
            return false;
        }
        return true;  
    }
    
    public int hashCode(){
        int result = hours!=null ? hours.hashCode() : 0;
        result = 31 * result + (timesheet !=null ? timesheet.hashCode():0);
        return result;
    }
}
