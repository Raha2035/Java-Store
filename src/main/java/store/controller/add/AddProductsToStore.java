package store.controller.add;

import store.controller.select.ShowAllProducts;
import store.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class AddProductsToStore implements Operation {

    private int storeID;
    public AddProductsToStore(int storeID) {
        this.storeID = storeID;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        try {
            HashMap<Product, Integer> productMap = new HashMap<>();
            String answer = "y";
            do {
                System.out.println("Enter product ID (int): -1 to show all products");
                int productId = scanner.nextInt();

                while(productId == -1){
                    new ShowAllProducts().operation(database, scanner, user);
                    System.out.println("Enter product ID (int): -1 to show all products");
                    productId = scanner.nextInt();
                }


                System.out.println("Enter product inventory:");
                int productInventory = scanner.nextInt();

                String getProductQuery = "SELECT * FROM `store_schema`.`product` WHERE PID = " + productId + ";";
                ResultSet productResult = database.getStatement().executeQuery(getProductQuery);
                productResult.next();

                Product product = new Product();
                product.setID(productResult.getInt("PID"));
                product.setName(productResult.getString("productName"));
                product.setFee(productResult.getDouble("fee"));

                int categoryId = productResult.getInt("category");
                String getCategoryQuery = "SELECT * FROM `store_schema`.`category` WHERE CID = " + categoryId + ";";
                ResultSet categoryResult = database.getStatement().executeQuery(getCategoryQuery);
                categoryResult.next();

                Category category = new Category();
                category.setID(categoryResult.getInt("CID"));
                category.setName(categoryResult.getString("categoryName"));
                category.setValue(categoryResult.getByte("value"));
                category.setAvailable(categoryResult.getByte("cAvailable"));
                product.setCategory(category);

                productMap.put(product, productInventory);
                System.out.println("Do you want to add products again? (y/n)");
                answer = scanner.next();

            } while (answer.equals("y") || answer.equals("Y"));

            for (Product product : productMap.keySet()){
                String getIDQuery = "SELECT COUNT(*) From `store_schema`.`storeproducts`;";
                ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
                resultSet.next();
                int storeProductsID = resultSet.getInt("COUNT(*)");

                String insertStoreProductsQuery = "INSERT INTO `store_schema`.`storeproducts` (`SPID`, `sotre`, `product`, `inventory`)\n" +
                        "VALUES (" +  storeProductsID + ", " + storeID + ", " + product.getID() + ", " + productMap.get(product) + ");";
                database.getStatement().execute(insertStoreProductsQuery);
            }
        }catch (SQLException e){
            e.printStackTrace();
            try {
                String deleteQuery = "DELETE FROM `store_schema`.`store` WHERE `SID` =" + storeID + ";";
                database.getStatement().execute(deleteQuery);
            }catch (SQLException error){
                error.printStackTrace();
            }
        }
    }
}
