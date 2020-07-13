package org.example.example.example2;

import java.io.File;
import java.util.function.Consumer;

public interface ConfigLoader {

    // load a config asynchronously
    void load(File source, Consumer<Config> callback);

}
