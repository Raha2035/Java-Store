package com.example.store.controller.delete;

import com.example.store.controller.select.ShowAllProducts;
import com.example.store.model.Operation;
import com.example.store.model.User;
import com.example.store.model.dao.Database;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class DeleteProduct implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter product ID (int): -1 to show all products");
        int productID = scanner.nextInt();

        while(productID == -1){
            new ShowAllProducts().operation(database, scanner, user);
            System.out.println("Enter product ID (int): -1 to show all products");
            productID = scanner.nextInt();
        }
        try {
            String deleteCategoryQuery = "DELETE FROM `store_schema`.`product`\n" +
                    "WHERE PID = "+ productID +";";
            database.getStatement().execute(deleteCategoryQuery);

            System.out.println("Item deleted successfully.");
        } catch (SQLException e){
            if(e instanceof SQLIntegrityConstraintViolationException){
                System.out.println("This item is in use and cannot be deleted.");
            } else{
                e.printStackTrace();
            }
        }
    }
}
