package com.example.store.controller.select;

import com.example.store.model.Category;
import com.example.store.model.dao.Database;
import com.example.store.model.Operation;
import com.example.store.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewCategories implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("All categories:");
        ArrayList<Category> categories = new ArrayList<>();

        try {
            String getAllCategoriesQuery = "SELECT * FROM `store_schema`.`category`;";
            ResultSet categoriesRows = database.getStatement().executeQuery(getAllCategoriesQuery);

            if(!categoriesRows.isBeforeFirst()){
                System.out.println("\nThere are no categories.");
                System.out.println("Please add a category first ");
                user.showList(database, scanner);
            } else {
                while (categoriesRows.next()){
                    Category category = new Category();
                    category.setID(categoriesRows.getInt("CID"));
                    category.setName(categoriesRows.getString("categoryName"));
                    category.setValue(categoriesRows.getByte("value"));
                    category.setAvailable(categoriesRows.getByte("cAvailable"));

                    categories.add(category);
                }

                for (Category category : categories){
                    if(category.getAvailable() == 1){
                        System.out.println("ID:\t" + category.getID());
                        System.out.println("Name:\t" + category.getName());
                        System.out.println("value:\t" + category.getValue());
                        System.out.println("---------------");
                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
