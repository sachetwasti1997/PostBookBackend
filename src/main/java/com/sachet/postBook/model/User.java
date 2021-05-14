package com.sachet.postBook.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.sachet.postBook.config.Views;
import com.sachet.postBook.custom_constraints.EmailConstraint;
import com.sachet.postBook.custom_constraints.UserNameConstraint;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User extends BaseEntity{

    private static final long serialVersionUUID = 4074374728582967483L;

    @NotNull(message = "User Name cannot be null")
    @Column(name = "user_name")
    @Size(min = 4, max = 255, message = "Username must have more then four characters, and have less then 256" +
            " characters")
    @UserNameConstraint(message = "User name already taken")
    private String userName;

    @NotNull
    @Column(name = "display_name")
    @Size(min = 4, max = 255, message = "Displayname must have more then four characters, and have less then 256" +
            " characters")
    private String displayName;

    @NotNull(message = "Email cannot be null")
    @Column(name = "email")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter a valid" +
            " email")
    @EmailConstraint(message = "Email Already taken")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password")
    @Size(min = 5, max = 255, message = "Password must be 5 character long, less than 50 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).*$",
            message = "Password must have one lower case letter, one upper case letter, one digit and a special " +
                    "character. It must have more then five characters and less than 256 characters.")
    private String password;

    @Size(max=255)
    private String image;

    public User() {
    }

    public User(String userName, String displayName, String email, String password) {
        this.userName = userName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
