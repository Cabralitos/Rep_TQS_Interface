
package com.mycompany.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Carlos Cabral
 */
public class Task implements Serializable{
   
   private int idtask;
   private int complete;
   private String description;
   private Date dueDate;
   private Category idcategory;
   private Priority idpriority;


    public String getIdcategory() {
        return idcategory.getName();
    }

    public void setIdcategory(Category idcategory) {
        this.idcategory = idcategory;
    }

    public int getIdpriority() {
        return idpriority.getValue();
    }

    public void setIdpriority(Priority idpriority) {
        this.idpriority = idpriority;
    }


    public Task() {
       
    }

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDuedate() {
        return dueDate;
    }

    public void setDuedate(Date duedate) {
        this.dueDate = duedate;
    }
   
   
   
}
