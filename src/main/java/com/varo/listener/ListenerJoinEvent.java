package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ListenerJoinEvent implements Listener {
    private final Plugin plugin;
    private final ChatUtil chatUtil;

    public ListenerJoinEvent(Plugin plugin) {
        this.plugin = plugin;
        chatUtil = new ChatUtil();
    }

    @EventHandler
    public void playerJoined(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.DARK_AQUA + event.getPlayer().getName() + ChatColor.YELLOW + " hat den Server betreten!");
        if (Game.instance().getCurrent() == GameState.INGAME) {

            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                int counter = 15;

                @Override
                public void run() {
                    if (counter == 0) {
                        chatUtil.sendAllPlayers(ChatColor.DARK_AQUA + event.getPlayer().getName() + ChatColor.YELLOW + " ist jetzt angreifbar!");
                        scheduler.cancelTasks(plugin);
                    } else if (counter % 5 == 0) {
                        chatUtil.sendAllPlayers(ChatColor.DARK_AQUA + event.getPlayer().getName() + ChatColor.YELLOW + " ist in " + counter + " Sekunden angreifbar!");
                    }
                    counter--;
                }
            }, 0L, 20L);
        }
    }
}
