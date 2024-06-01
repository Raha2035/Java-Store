package store.controller.add;

import store.controller.select.ShowAllProducts;
import store.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class AddNewStore implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Add new store");
        System.out.println("-------------");

        System.out.println("Enter store name:");
        String storeName = scanner.next();

        int storeID = -1;
        try {
            ArrayList<String> storeNames = new ArrayList<>();
            String getStoresQuery = "SELECT `storeName` FROM `store_schema`.`store`;";
            ResultSet storesResult = database.getStatement().executeQuery(getStoresQuery);
            while(storesResult.next()){
                storeNames.add(storesResult.getString("storeName"));
            }

            if(storeNames.contains(storeName)){
                System.out.println("This store is already exist");
                return;
            }

            String getIDQuery = "SELECT COUNT(*) From `store_schema`.`store`;";
            ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
            resultSet.next();
            storeID = resultSet.getInt("COUNT(*)");

            String insertStoreQuery = "INSERT INTO `store_schema`.`store` (`SID`, `storeName`)\n" +
                                        "VALUES (" + storeID +  ", '" +  storeName + "');";
            database.getStatement().execute(insertStoreQuery);

            System.out.println("Store added successfully");

            new AddProductsToStore(storeID).operation(database,scanner,user);

        }catch (SQLException e){
            e.printStackTrace();
        }






    }
}
