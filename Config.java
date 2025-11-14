package com.koushani.utility.utils;

/**
 * Simple Singleton configuration holder.
 */
public final class Config {
    private static final Config INSTANCE = new Config();

    private Config() {
        // load properties in future if needed
    }

    public static Config get() {
        return INSTANCE;
    }

    public int getMaxThreads() {
        return 4;
    }
}
