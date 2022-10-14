package dev.rmmarquini.aws.s3.utils;

import dev.rmmarquini.aws.s3.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private final Properties prop;

    public ConfigProperties() {
        this.prop = new Properties();
    }

    public Properties load() {

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("Unable to find config.properties.");
            }

            prop.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.prop;
    }

}
