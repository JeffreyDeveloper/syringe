package org.example.example.example1;

import me.yushust.inject.bind.Binder;
import me.yushust.inject.bind.Module;

public class MyModule implements Module {

    private final boolean useMySql;

    public MyModule(boolean useMySql) {
        this.useMySql = useMySql;
    }

    @Override
    public void configure(Binder binder) {

        if (useMySql) {
            binder.bind(Database.class).to(MySQLDatabase.class);
        } else {
            binder.bind(Database.class).to(MongoDatabase.class);
        }

    }

}
