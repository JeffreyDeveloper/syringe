package org.example.example.example2;

import team.unnamed.inject.Inject;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class JsonConfigLoader implements ConfigLoader {

    @Inject private Executor executor;

    @Override
    public void load(File source, Consumer<Config> callback) {
        executor.execute(() -> {

            Config config = null;

            // Load the config from a JSON file...
            try {
                Thread.sleep(500); // simulates an operation
                config = new Config(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            callback.accept(config);

        });
    }

}
