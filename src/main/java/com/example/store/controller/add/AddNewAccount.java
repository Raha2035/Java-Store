package com.example.store.controller.add;

import com.example.store.model.Customer;
import com.example.store.model.dao.Database;
import com.example.store.model.User;
import com.example.store.model.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AddNewAccount implements Operation {

    private int accType;

    public AddNewAccount(int accType) {
        this.accType = accType;
    }

    /**
     * @param database
     * @param scanner
     * @param user
     */
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter first name:");
        String firstName = scanner.next();

        System.out.println("Enter last name:");
        String lastName = scanner.next();

        System.out.println("Enter email:");
        String email = scanner.next();

        System.out.println("Enter password:");
        String password = scanner.next();

        System.out.println("Confirm password:");
        String confirmPassword = scanner.next();

        while (!password.equals(confirmPassword)){
            System.out.println("password doesn't match");

            System.out.println("Enter password:");
            password = scanner.next();

            System.out.println("Confirm password:");
            confirmPassword = scanner.next();
        }

        try {
            ArrayList<String> emails = new ArrayList<>();
            String getEmailsQuery = "SELECT `email` FROM `store_schema`.`user`;";
            ResultSet emailsResult = database.getStatement().executeQuery(getEmailsQuery);
            while(emailsResult.next()){
                emails.add(emailsResult.getString("email"));
            }

            if(emails.contains(email)){
                System.out.println("This email is already used");
                return;
            }

            String getIDQuery = "SELECT COUNT(*) From `store_schema`.`user`;";
            ResultSet resultSet = database.getStatement().executeQuery(getIDQuery);
            resultSet.next();
            int id = resultSet.getInt("COUNT(*)");

            String insertUserQuery = "INSERT INTO `store_schema`.`user`\n" +
                    "(`UID`,`firstName`,`lastName`,`email`,`password`,`type`)\n" +
                    "VALUES\n" +
                    "(" + id +  ",\n '" +
                     firstName + "' ,\n '" +
                     lastName + "' ,\n '" +
                    email + "',\n '" +
                    password + "',\n" +
                    accType + ");";
            database.getStatement().execute(insertUserQuery);

            System.out.println("Account created successfully.\n");

            if(accType == 0){
                user = new Customer();
                user.setID(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);

                user.showList(database, scanner);
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
