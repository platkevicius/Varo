package com.varo.models;

import com.varo.sql.managers.BorderSQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;

public class Border {

    private double radius;
    private WorldBorder worldBorder;

    private final BorderSQL borderSQL;

    public Border(BorderSQL borderSQL) {
        this.borderSQL = borderSQL;
    }

    public void createBorder(Location location, double radius) {
        if (location.getWorld() != null)
            worldBorder = Bukkit.getWorld(location.getWorld().getUID()).getWorldBorder();

        if (worldBorder == null)
            return;

        worldBorder.setCenter(location);
        worldBorder.setSize(radius);

        borderSQL.createBorder(location, radius);
    }

    public void decreseBorder() {
        radius -= 10;

        worldBorder.setSize(radius);
        borderSQL.updateRadius(radius);
    }

}
