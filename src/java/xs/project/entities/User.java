/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.project.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlRootElement
public class User implements Serializable {
    
    private int user_id;
    private String email;
    private String password;

    public User() {
    }

    public User(int user_id, String email, String password) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", email=" + email + ", password=" + password + '}';
    }

    
    
    

 }
