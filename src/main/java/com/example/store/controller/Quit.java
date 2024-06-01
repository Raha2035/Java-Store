package com.example.store.controller;

import com.example.store.model.Operation;
import com.example.store.model.User;
import com.example.store.model.dao.Database;

import java.util.Scanner;

public class Quit implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Thank you for shopping with us!");
        scanner.close();

    }
}
