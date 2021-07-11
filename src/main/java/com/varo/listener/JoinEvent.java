package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class JoinEvent implements Listener {
    private final Plugin plugin;
    private final ChatUtil chatUtil;

    public JoinEvent(Plugin plugin) {
        this.plugin = plugin;
        chatUtil = new ChatUtil();
    }

    @EventHandler
    public void playerJoined(PlayerJoinEvent event) {
        if (Game.instance().getCurrent() == GameState.WARMUP) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setHealth(20);
            event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat den Server betreten!");
        }
        else if (Game.instance().getCurrent() == GameState.INGAME) {
            event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat den Server betreten!");

            BukkitScheduler countdown = Bukkit.getScheduler();
            countdown.scheduleSyncRepeatingTask(plugin, new Runnable() {
                int counter = 15;

                @Override
                public void run() {
                    if (counter == 0) {
                        chatUtil.sendAllPlayers(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " ist jetzt angreifbar!");
                        countdown.cancelTasks(plugin);
                    } else if (counter % 5 == 0 || counter == 4 || counter == 3 || counter == 2 || counter == 1) {
                        chatUtil.sendAllPlayers(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " ist in " + ChatColor.RED + counter + ChatColor.RED + " Sekunden angreifbar!");
                    }
                    counter--;
                }
            }, 0L, 20L);

            BukkitScheduler timeLimit = Bukkit.getScheduler();
            timeLimit.scheduleSyncRepeatingTask(plugin, new Runnable() {
                int counter = 30;

                @Override
                public void run() {
                    if (counter == 0) {
                        chatUtil.sendAllPlayers(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat das Spiel verlassen");
                        event.getPlayer().kickPlayer("Deine heutige Zeit auf dem Server ist aufgebraucht");
                        timeLimit.cancelTasks(plugin);
                    }
                    else if (counter == 15 || counter == 10 || counter == 5 || counter == 4 || counter == 3 || counter == 2 || counter == 1) {
                        chatUtil.sendAllPlayers(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " wird in " + counter + " Sekunden vom Server gekickt!");
                    }
                    counter--;
                }
            }, 0L, 20L);
        }
    }
}
