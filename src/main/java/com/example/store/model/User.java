package com.example.store.model;

import com.example.store.model.dao.Database;

import java.util.Scanner;

abstract public class User {
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /*
    * type = 0 -> customer
    * type = 1 -> admin
    * type = 2 -> deletedClient
    * type = 3 -> deletedAdmin
    * */

    public User() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    abstract public void showList(Database database, Scanner scanner);
}
