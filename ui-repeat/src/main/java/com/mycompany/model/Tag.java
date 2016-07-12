
package com.mycompany.model;

import java.io.Serializable;

/**
 *
 * @author Carlos Cabral
 */
public class Tag implements Serializable{
    private int idtag;
    private String name;

    public Tag() {
    }

    public int getIdtag() {
        return idtag;
    }

    public void setIdtag(int idtag) {
        this.idtag = idtag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
