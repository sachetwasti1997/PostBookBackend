package com.sachet.postBook.model;

import javax.persistence.*;

@Entity
public class User extends BaseEntity{

    @Column(name = "user_name")
    private String userName;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "password")
    private String password;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
