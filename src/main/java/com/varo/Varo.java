package com.varo;

import com.varo.commands.Start;
import com.varo.listener.Basic;
import com.varo.listener.JoinEvent;
import com.varo.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Varo extends JavaPlugin {

    private Config config;

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        config = new Config();
        System.out.println("Plugin Varo started");

        config.setUp();
        registerCommands();
        registerListener();
    }


    private void registerCommands() {
        getCommand("start").setExecutor(new Start(this));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new Basic(), this);
    }

}
