package com.example.store.model;

import com.example.store.model.dao.Database;

import java.util.Scanner;

public interface Operation {
    public void operation(Database database, Scanner scanner, User user);
}
