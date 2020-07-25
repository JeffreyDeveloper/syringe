package org.example.example.example2;

import team.unnamed.inject.Inject;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class YamlConfigLoader implements ConfigLoader {

    @Inject private Executor executor;

    @Override
    public void load(File source, Consumer<Config> callback) {
        executor.execute(() -> {

            Config config = null;

            // Load the configuration
            try {
                Thread.sleep(1000); // simulates an operation
                config = new Config(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            callback.accept(config);

        });
    }

}
