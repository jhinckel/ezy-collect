package com.ezy.collect.config.database;

public class HikariPoolProperties {

    public static final String HIKARI_CONFIG_PATH = ".hikari.";

    public static final String SCHEMA = HIKARI_CONFIG_PATH + "schema";
    public static final String POOL_NAME = HIKARI_CONFIG_PATH + "pool-name";
    public static final String DRIVER = HIKARI_CONFIG_PATH + "driver";
    public static final String MINIMUM_IDLE = HIKARI_CONFIG_PATH + "minimum-idle";
    public static final String MAXIMUM_POOL_SIZE = HIKARI_CONFIG_PATH + "maximum-pool-size";
    public static final String IDLE_TIMEOUT = HIKARI_CONFIG_PATH + "idle-timeout";
    public static final String MAX_LIFETIME = HIKARI_CONFIG_PATH + "max-lifetime";
    public static final String READ_ONLY = HIKARI_CONFIG_PATH + "is-read-only";

}
