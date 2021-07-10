package com.varo;

import com.varo.commands.CommandCountdown;
import com.varo.listener.ListenerJoinEvent;
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
        //getCommand("start").setExecutor(new CommandCountdown());
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new ListenerJoinEvent(this), this);
    }

}
