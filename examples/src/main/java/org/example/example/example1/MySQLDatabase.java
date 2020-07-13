package org.example.example.example1;

public class MySQLDatabase implements Database {

    @Override
    public User find(int userId) {
        System.out.println("Searching in MySQL Database");
        // find a user in database
        return null;
    }

    @Override
    public void save(User user) {
        System.out.println("Saving user " + user.getName() + " in MySQL Database");
        // save the user in the database
    }

    @Override
    public void remove(int userId) {
        System.out.println("Removing user from MySQL Database...");
        // remove the user from the database
    }

}
