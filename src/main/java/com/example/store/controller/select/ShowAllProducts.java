package com.example.store.controller.select;

import com.example.store.model.*;
import com.example.store.model.dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShowAllProducts implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Show products:");

        try {
            ArrayList<Product> products = new ArrayList<>();
            String getAllProductsQuery = "SELECT * FROM `store_schema`.`product` p, `store_schema`.`category` c WHERE p.category = c.CID;";
            ResultSet productRows = database.getStatement().executeQuery(getAllProductsQuery);
            if (!productRows.isBeforeFirst()){
                System.out.println("\nThere are no products.");
                System.out.println("Please add a product first ");
                user.showList(database, scanner);
            } else {
                HashMap<Product, Integer> productCategoryIdMap = new HashMap<>();
                while (productRows.next()){
                    Product product = new Product();
                    product.setID(productRows.getInt("PID"));
                    product.setName(productRows.getString("productName"));
                    int categoryId = productRows.getInt("category");
                    product.setCategory(null);
                    product.setFee(productRows.getDouble("fee"));
                    products.add(product);
                    productCategoryIdMap.put(product, categoryId);
                }

                for (Product product : products){
                    String getCategoryQuery = "SELECT * FROM `store_schema`.`category` WHERE CID =" + productCategoryIdMap.get(product) + ";";
                    ResultSet categoryRow = database.getStatement().executeQuery(getCategoryQuery);
                    categoryRow.next();
                    Category category = new Category();
                    category.setID(categoryRow.getInt("CID"));
                    category.setName(categoryRow.getString("categoryName"));
                    category.setValue(categoryRow.getByte("value"));
                    product.setCategory(category);

                    System.out.println("productID:\t" + product.getID());
                    System.out.println("productName:\t" + product.getName());
                    System.out.println("productCategory:\t" + product.getCategory().getName());
                    System.out.println("productPrice:\t" + product.getFee());
                    System.out.println("-----------------");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
