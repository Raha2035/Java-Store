package store.controller.add;

import store.model.Database;
import store.model.Operation;
import store.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewCategory implements Operation {
    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Add new category");
        System.out.println("----------------");

        System.out.println("Enter name:");
        String categoryName = scanner.next();

        try {
            String getIDQuery = "SELECT COUNT(*) From `store_schema`.`category`;";
            ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
            resultSet.next();
            int id = resultSet.getInt("COUNT(*)");

            int categoryValue = ++id;

            String insertCategoryQuery = "INSERT INTO `store_schema`.`category` (`CID`, `categoryName`, `value`, `cAvailable`)\n" +
                    "VALUES (" + id + ", '" + categoryName + "', " + categoryValue +  ", " + 1 + ");";
            database.getStatement().execute(insertCategoryQuery);

            System.out.println("Category added successfully.\n");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
