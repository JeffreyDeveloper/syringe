package org.example.example.example1;

public interface Database {

    User find(int userId);

    void save(User user);

    void remove(int userId);

}
