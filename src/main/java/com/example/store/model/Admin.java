package com.example.store.model;

import com.example.store.controller.Quit;
import com.example.store.controller.add.AddNewAccount;
import com.example.store.controller.add.AddNewCategory;
import com.example.store.controller.add.AddNewProduct;
import com.example.store.controller.add.AddNewStore;
import com.example.store.controller.delete.DeleteCategory;
import com.example.store.controller.delete.DeleteProduct;
import com.example.store.controller.delete.DeleteStore;
import com.example.store.model.dao.Database;

import java.util.Scanner;

public class Admin extends User{

    private Operation[] operations = new Operation[] {
            new AddNewCategory(),
            new DeleteCategory(),
            new AddNewProduct(),
            new DeleteProduct(),
            new AddNewStore(),
            new DeleteStore(),
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
        System.out.println("02. Delete category");
        System.out.println("03. Add new product");
        System.out.println("04. Delete product");
        System.out.println("05. Add new store");
        System.out.println("06. Delete store");
        System.out.println("07. Add new Admin");
        System.out.println("08. Quit");

        int i = scanner.nextInt();

        if(i<1 || i>8){
            showList(database,scanner);
            return;
        }

        operations[i-1].operation(database, scanner, this);
        if(i != 8){
            showList(database,scanner);
        }

    }
}
