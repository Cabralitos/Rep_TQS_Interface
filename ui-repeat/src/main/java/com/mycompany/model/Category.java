
package com.mycompany.model;

import java.io.Serializable;

/**
 *
 * @author Carlos Cabral
 */
public class Category implements Serializable{
    private int idcategory;
    private String name;

    public Category() {
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
