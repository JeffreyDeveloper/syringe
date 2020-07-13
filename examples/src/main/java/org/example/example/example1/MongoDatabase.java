package org.example.example.example1;

public class MongoDatabase implements Database {

    @Override
    public User find(int userId) {
        System.out.println("Searching user in MongoDB");
        // find user...
        return null;
    }

    @Override
    public void save(User user) {
        System.out.println("Saving user in MongoDB");
        // save user...
    }

    @Override
    public void remove(int userId) {
        System.out.println("Removing user from MongoDB");
        // remove user...
    }

}
