package com.example.store.model;

import com.example.store.controller.Quit;
import com.example.store.controller.add.AddNewAccount;
import com.example.store.controller.add.AddNewCategory;
import com.example.store.controller.add.AddNewProduct;
import com.example.store.controller.add.AddNewStore;
import com.example.store.model.dao.Database;

import java.util.Scanner;

public class Admin extends User{

    private Operation[] operations = new Operation[] {
            new AddNewCategory(),
            new AddNewProduct(),
            new AddNewStore(),
            new AddNewAccount(1),
            new Quit()
    };
    public Admin() {
        super();
    }

    /**
     * @param database
     * @param scanner
     */
    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("\n01. Add new category");
        System.out.println("02. Add new product");
        System.out.println("03. Add new store");
        System.out.println("04. Add new Admin");
        System.out.println("05. Quit");

        int i = scanner.nextInt();

        if(i<1 || i>5){
            showList(database,scanner);
            return;
        }

        operations[i-1].operation(database, scanner, this);
        if(i != 5){
            showList(database,scanner);
        }

    }
}
