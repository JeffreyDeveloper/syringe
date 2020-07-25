package org.example.example.example2;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    @Inject private ConfigLoader configLoader;

    public void start() {

        AtomicBoolean end = new AtomicBoolean(false);

        // using lambda for the callback
        configLoader.load(new File("C://some/path/for/config.json"), config -> {
            if (config.shouldPrintHelloWorld()) {
                System.out.println("Hello world!");
            }
            end.set(true);
        });

        while (!end.get()) {
            // make the thread wait for end
        }

        System.exit(0);

    }

    public static void main(String[] args) {

        Injector injector = InjectorFactory.create(binder -> { // create a module using lambda
            // bindings to instance are singleton
            binder.bind(Executor.class).toInstance(Executors.newCachedThreadPool());
            // binder.bind(ConfigLoader.class).to(JsonConfigLoader.class);
            binder.bind(ConfigLoader.class).to(YamlConfigLoader.class);
        });

        Main main = injector.getInstance(Main.class);
        main.start();

    }

}
