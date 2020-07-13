package org.example.example.example2;

public class Config {

    private final boolean printHelloWorld;

    public Config(boolean printHelloWorld) {
        this.printHelloWorld = printHelloWorld;
    }

    public boolean shouldPrintHelloWorld() {
        return printHelloWorld;
    }

}
