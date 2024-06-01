package store.model;

import store.controller.add.AddNewAccount;
import store.controller.add.AddNewCategory;
import store.controller.add.AddNewProduct;
import store.controller.add.AddNewStore;

import java.util.Scanner;

public class Admin extends User{

    private Operation[] operations = new Operation[] {
            new AddNewCategory(),
            new AddNewProduct(),
            new AddNewStore(),
            new AddNewAccount(1)
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
        System.out.println("05.Edit my data");
        System.out.println("06. Quit");

        int i = scanner.nextInt();

        if(i<1 || i>6){
            showList(database,scanner);
            return;
        }

        operations[i-1].operation(database, scanner, this);
        if(i != 6){
            showList(database,scanner);
        }

    }
}
