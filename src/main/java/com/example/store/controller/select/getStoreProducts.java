package com.example.store.controller.select;

import com.example.store.model.dao.Database;
import com.example.store.model.Operation;
import com.example.store.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class getStoreProducts implements Operation {

    private int storeID = -1;

    public getStoreProducts(int storeID) {
        this.storeID = storeID;
    }

    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        String getProductsOfStore = "SELECT * FROM ( SELECT * FROM (select * FROM `store_schema`.`storeproducts` WHERE store = " + storeID
                + " and inventory > 0) sp, `store_schema`.`product` p WHERE sp.product = p.PID ) tOne,  `store_schema`.`category` c \n"
                + "WHERE tOne.category = c.CID; ";
        try {
            ResultSet result = database.getStatement().executeQuery(getProductsOfStore);
            while (result.next()){
                System.out.println("Product ID:\t" + result.getInt("PID"));
                System.out.println("Product name:\t" + result.getString("productName"));
                System.out.println("Product fee:\t" + result.getDouble("fee"));
                System.out.println("Balance:\t" + result.getInt("inventory"));
                System.out.println("Product category:\t" + result.getString("categoryName"));
                System.out.println("--------------------");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
