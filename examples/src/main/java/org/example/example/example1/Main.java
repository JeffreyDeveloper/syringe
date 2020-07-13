package org.example.example.example1;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;

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
