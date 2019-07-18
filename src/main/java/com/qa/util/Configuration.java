package com.qa.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Configuration {
    private static Config conf = ConfigFactory.load("default.conf");

    /**
     * Read property value from configuration file default.conf
     * @param property String
     * @return property value
     */
    public static String getProperty(String property)
    {
        return ((System.getProperty(property) == null) ? conf.getString(property) : System.getProperty(property));
    }
}
