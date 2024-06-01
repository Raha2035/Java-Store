package store.controller;
import store.controller.add.AddNewAccount;
import store.model.Admin;
import store.model.Customer;
import store.model.Database;
import store.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to ShopCenter system");
        System.out.println("Enter your email:\n (-1 to create new account)");
        String email = scanner.next();

        if(email.equals("-1")) {
            new AddNewAccount(0).operation(database, scanner, null);
            return;
        }

        System.out.println("Enter password:");
        String password = scanner.next();

        ArrayList<User> users = new ArrayList<>();

        try {
            String getAllUsersQuery = "SELECT * FROM `store_schema`.`user`;";
            ResultSet usersResult = database.getStatement().executeQuery(getAllUsersQuery);
            while (usersResult.next()){
                User user;
                int id = usersResult.getInt("UID");
                String firstName = usersResult.getString("firstName");
                String lastName = usersResult.getString("lastName");
                String userEmail = usersResult.getString("email");
                String userPass = usersResult.getString("password");
                int type = usersResult.getInt("type");

                if(type == 0){
                    user = new Customer();
                    user.setID(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(userEmail);
                    user.setPassword(userPass);

                    users.add(user);//
                } else if (type == 1) {
                    user = new Admin();
                    user.setID(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(userEmail);
                    user.setPassword(userPass);

                    users.add(user);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        boolean loggedIn = false;

        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                System.out.println("Welcome " + user.getFirstName() + "!");
                loggedIn = true;
                user.showList(database, scanner);
            }
        }

        if(!loggedIn){
            System.out.println("Email or password doesn't match");
            scanner.close();
        }
    }
}
