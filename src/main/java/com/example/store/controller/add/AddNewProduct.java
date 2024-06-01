package com.example.store.controller.add;

import com.example.store.controller.select.ViewCategories;
import com.example.store.model.dao.Database;
import com.example.store.model.User;
import com.example.store.model.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewProduct implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {

        System.out.println("Add new Product");
        System.out.println("---------------");

        System.out.println("Enter product name");
        String productName = scanner.next();

        System.out.println("Enter product category ID (int): -1 to show all category");
        int productCategory = scanner.nextInt();

        while(productCategory == -1){
            new ViewCategories().operation(database,scanner,user);
            System.out.println("Enter product category ID (int): -1 to show all category");
            productCategory = scanner.nextInt();
        }

        System.out.println("Enter product's fee (double):");
        double productFee = scanner.nextDouble();

        try {
            String getIDQuery = "SELECT COUNT(*) From `store_schema`.`product`;";
            ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
            resultSet.next();
            int id = resultSet.getInt("COUNT(*)");

            String insertCategoryQuery = "INSERT INTO `store_schema`.`product` (`PID`,  `productName`, `category`, `fee`, `pAvailable`)\n" +
                                         "VALUES (" + id + ", '" + productName + "', " + productCategory + ", " + productFee + ", " + 1 + ")";
            database.getStatement().execute(insertCategoryQuery);

            System.out.println("Product added successfully.\n");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
