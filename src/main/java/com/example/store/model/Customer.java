package com.example.store.model;

import com.example.store.controller.Quit;
import com.example.store.controller.order.Shop;
import com.example.store.controller.select.ViewStores;
import com.example.store.model.dao.Database;

import java.util.Scanner;

public class Customer extends User{

    private Operation[] operations = new Operation[] {
            new ViewStores(),
            new Shop(),
            new Quit(),
    };

    public Customer() {
        super();
    }

    /**
     * @param database
     * @param scanner
     */
    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("01. View stores");
        System.out.println("02. Shopping");
        System.out.println("03. Quit");

        int i = scanner.nextInt();

        if(i<1 || i>3){
            showList(database,scanner);
            return;
        }

        operations[i-1].operation(database,scanner,this);

        if(i != 3){
            showList(database,scanner);
        }
    }
}
