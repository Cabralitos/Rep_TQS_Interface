
package com.mycompany.model;

import java.io.Serializable;

/**
 *
 * @author Carlos Cabral
 */
public class Priority implements Serializable{
    private int idpriority;
    private String name;
    private int value;

    public Priority() {
    }

    public int getIdpriority() {
        return idpriority;
    }

    public void setIdpriority(int idpriority) {
        this.idpriority = idpriority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
