package store.controller.select;

import store.model.Database;
import store.model.Operation;
import store.model.Store;
import store.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewStores implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        try {
            ArrayList<Store> stores = new ArrayList<>();
            String getStoresQuery = "SELECT * FROM `store_schema`.`store`;";
            ResultSet storesResult = database.getStatement().executeQuery(getStoresQuery);
            if(!storesResult.isBeforeFirst()){
                System.out.println("There are no stores");
            }
            while(storesResult.next()){
                Store store = new Store();
                store.setID(storesResult.getInt("SID"));
                store.setName(storesResult.getString("storeName"));
                store.setProducts(null);
                stores.add(store);
            }

            for (Store store : stores){
                System.out.println("\nStore ID:\t" + store.getID());
                System.out.println("Store name:\t" + store.getName());
                System.out.println("-------------");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
