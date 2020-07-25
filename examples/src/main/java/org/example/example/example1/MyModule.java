package org.example.example.example1;

import team.unnamed.inject.bind.Binder;
import team.unnamed.inject.bind.Module;

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
