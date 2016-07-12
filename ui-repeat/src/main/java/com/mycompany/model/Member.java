
package com.mycompany.model;

import java.io.Serializable;

/**
 *
 * @author Carlos Cabral
 */
public class Member implements Serializable{
    
    private int idmember;
    private String username;
    private String password;

    public Member() {
    }

    public int getMemberid() {
        return idmember;
    }

    public void setMemberid(int memberid) {
        this.idmember = memberid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
