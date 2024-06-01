package com.example.store.controller.delete;

import com.example.store.controller.select.ViewCategories;
import com.example.store.model.Operation;
import com.example.store.model.User;
import com.example.store.model.dao.Database;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class DeleteCategory implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter category ID (int): -1 to show all categories");
        int categoryId = scanner.nextInt();

        while(categoryId == -1){
            new ViewCategories().operation(database, scanner, user);
            System.out.println("Enter category ID (int): -1 to show all categories");
            categoryId = scanner.nextInt();
        }
        try {
            String deleteCategoryQuery = "DELETE FROM `store_schema`.`category`\n" +
                    "WHERE CID = "+ categoryId +";";
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
