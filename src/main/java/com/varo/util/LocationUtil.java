package com.varo.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationUtil {

    private final File file;
    private final FileConfiguration configuration;

    public LocationUtil() {
        file = new File("plugins/Varo", "locations.yml");
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void setLocation(String path, Location location) {
        if (location == null)
            throw new IllegalArgumentException("Location can't be null!");

        configuration.set(path + ".world", location.getWorld().getName());
        configuration.set(path + ".x", location.getX());
        configuration.set(path + ".y", location.getY());
        configuration.set(path + ".z", location.getZ());

        configuration.set(path + ".yaw", location.getYaw());
        configuration.set(path + ".pitch", location.getPitch());

        try {
            configuration.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String path) {
        String world = configuration.getString(path + ".world");
        double x = configuration.getDouble(path + ".x");
        double y = configuration.getDouble(path + ".y");
        double z = configuration.getDouble(path + ".z");

        double yaw = configuration.getDouble(path + ".yaw");
        double pitch = configuration.getDouble(path + ".pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

}