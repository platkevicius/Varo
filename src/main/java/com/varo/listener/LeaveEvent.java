package com.varo.listener;

import com.varo.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class LeaveEvent implements Listener {
    private Plugin plugin;

    public LeaveEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void disconnect(PlayerQuitEvent event) {
        Game.instance().getServerTime().get(event.getPlayer()).getE().cancelTasks(plugin);
    }
}
