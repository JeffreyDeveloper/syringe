package org.example.example.example1;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;

public class Main {

    @Inject private Database database;

    public void start() {

        User user = new User(0, "somebody");
        database.save(user);
    }

    public static void main(String[] args) {

        Injector injector = InjectorFactory.create(
                new MyModule(true) // test it with "false"
        );
        Main main = injector.getInstance(Main.class);

        main.start();

    }

}
