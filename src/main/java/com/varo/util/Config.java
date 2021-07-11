package com.varo.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private final File file;
    private final FileConfiguration configuration;

    public Config() {
        this.file = new File("plugins/Varo", "config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void setUp() {
        configuration.options().copyDefaults(true);
        configuration.addDefault("prefix", "[Varo] ");

        try {
            configuration.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
