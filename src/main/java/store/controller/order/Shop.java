package store.controller.order;

import store.controller.select.ViewStores;
import store.controller.select.getStoreProducts;
import store.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Shop implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter store ID (int): -1 to view stores");
        int storeId = scanner.nextInt();

        while (storeId == -1){
            new ViewStores().operation(database,scanner,user);
            System.out.println("Enter store ID (int): -1 to view stores");
            storeId = scanner.nextInt();
        }

        System.out.println("Enter product ID (int): -1 to show all products");
        int productId = scanner.nextInt();

        while(productId == -1){
            new getStoreProducts(storeId).operation(database,scanner,user);
            System.out.println("Enter product ID (int): -1 to show all products");
            productId = scanner.nextInt();
        }

        System.out.println("Enter the product number (int):");
        int productNumber = scanner.nextInt();

        try {
            String selectProductOfStoreQuery = "select inventory FROM `store_schema`.`storeproducts` WHERE store = " + storeId + " and product = " + productId + ";";
            ResultSet result = database.getStatement().executeQuery(selectProductOfStoreQuery);
            result.next();
            int inventory = result.getInt("inventory");
            while (productNumber > inventory) {
                System.out.println("The total balance of this product in the store is:" + inventory);
                System.out.println("Enter the product number (int): This number must be less than or equal to " + inventory);
                productNumber = scanner.nextInt();
            }

            ShoppingCart cart = new ShoppingCart();
            cart.setUser(user);

            Product product = new Product();

            String getUserCartQuery = "SELECT * FROM (SELECT * FROM " +
                                        "(SELECT * FROM `store_schema`.`shoppingcart` sc " +
                                        "WHERE sc.user = "+ user.getID() +" and sc.store = "+ storeId + " and sc.product = " + productId + ") s, `store_schema`.`product` p " +
                                        " WHERE s.product = p.PID) t,  `store_schema`.`category` c WHERE t.category = c.CID; ";
            ResultSet cartResult = database.getStatement().executeQuery(getUserCartQuery);
            if(!cartResult.isBeforeFirst()){
                cart.setID(-1);
            } else {
                cartResult.next();
                product.setFee(cartResult.getDouble("fee"));
                cart.setID(cartResult.getInt("SCID"));
            }

            if(cart.getID() == -1) {
                String getIDQuery = "SELECT COUNT(*) From `store_schema`.`shoppingcart`;";
                ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
                resultSet.next();
                int id = resultSet.getInt("COUNT(*)");
                String insertQuery = "INSERT INTO `store_schema`.`shoppingcart` \n" +
                        "(`SCID`, `store`, `user`, `product`, `count`, `totalPrice`)\n" +
                        "VALUES ("+ id +", " + storeId + ", " + user.getID() + ", " + productId + ", " + productNumber + ", " + product.getFee() * productNumber + ");";
                database.getStatement().execute(insertQuery);
            } else {
                String updateShoppingCart = "UPDATE `store_schema`.`shoppingcart`\n" +
                        "SET `count` = 0, `totalPrice` = 0\n" +
                        "WHERE `SCID` = 0;";
                database.getStatement().execute(updateShoppingCart);
            }

            String updateStoreProducts = "UPDATE `store_schema`.`storeproducts` \n" +
                    "SET\n" +
                    "`inventory` =" + (inventory - 1) + " WHERE `store` = " +  storeId + " and `product` = " + productId + ";";
            database.getStatement().execute(updateStoreProducts);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
