package store.model;

import store.controller.add.AddNewAccount;
import store.controller.order.Shop;
import store.controller.select.ViewStores;

import java.util.Scanner;

public class Customer extends User{

    private Operation[] operations = new Operation[] {
            new ViewStores(),
            new Shop(),
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
        System.out.println("03. Change password");
        System.out.println("04.Edit my data");
        System.out.println("06. Quit");

        int i = scanner.nextInt();

        if(i<1 || i>6){
            showList(database,scanner);
            return;
        }

        operations[i-1].operation(database,scanner,this);

        if(i != 6){
            showList(database,scanner);
        }
    }
}
