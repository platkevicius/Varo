package com.varo;

import com.varo.commands.Start;
import com.varo.listener.JoinEvent;
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
        getCommand("start").setExecutor(new Start(this));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
    }

}
