package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import com.varo.runnables.CountdownLogin;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
            Game.instance().getInvulnerable().add(event.getPlayer().getUniqueId());
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setHealth(20);
            event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat den Server betreten!");

        } else if (Game.instance().getCurrent() == GameState.INGAME) {
            if (Game.instance().getPlayTimeUsedUp().contains(event.getPlayer().getUniqueId()))
                event.getPlayer().kickPlayer("Deine heutige Spielzeit ist aufgebraucht!");

            else {
                event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat den Server betreten!");

                if (!Game.instance().getAlreadyJoined().contains(event.getPlayer().getUniqueId()) && !Game.instance().getInvulnerable().contains(event.getPlayer().getUniqueId())) {
                    Game.instance().getInvulnerable().add(event.getPlayer().getUniqueId());
                    Game.instance().getAlreadyJoined().add(event.getPlayer().getUniqueId());

                    event.getPlayer().setGameMode(GameMode.ADVENTURE);

                    BukkitScheduler countdown = Bukkit.getScheduler();

                    CountdownLogin countdownLogin = new CountdownLogin(plugin, event.getPlayer());
                    final int id = countdown.scheduleSyncRepeatingTask(plugin, countdownLogin, 0L, 20L);
                    countdownLogin.setTaskID(id);
                }
            }
        }
    }
}
