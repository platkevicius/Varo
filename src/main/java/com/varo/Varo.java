package com.varo;

import org.bukkit.plugin.java.JavaPlugin;

public class Varo extends JavaPlugin {

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        System.out.println("Plugin Varo started");

        registerCommands();
        registerListener();
    }


    private void registerCommands() {

    }

    private void registerListener() {

    }

}
