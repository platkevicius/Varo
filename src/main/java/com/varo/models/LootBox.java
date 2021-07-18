package com.varo.models;

import org.bukkit.Location;

public class LootBox {

    private final int id;
    private final Location location;
    private boolean opened;

    public LootBox(int id, Location location) {
        this(id, location, false);
    }

    public LootBox(int id, Location location, boolean opened) {
        this.id = id;
        this.location = location;
        this.opened = opened;
    }

    public int getId() { return id; }

    public Location getLocation() {
        return location;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
