package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import com.varo.runnables.CountdownLogin;
import com.varo.sql.managers.UserSQL;
import com.varo.util.ChatUtil;
import com.varo.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Locale;

public class JoinEvent implements Listener {
    private final Plugin plugin;
    private final ChatUtil chatUtil;
    private LocationUtil locationUtil;
    private UserSQL userSQL;

    public JoinEvent(Plugin plugin, UserSQL userSQL) {
        this.plugin = plugin;
        chatUtil = new ChatUtil();
        locationUtil = new LocationUtil();
        this.userSQL = userSQL;
    }

    @EventHandler
    public void playerJoined(PlayerJoinEvent event) {
        if (Game.instance().getCurrent() == GameState.WARMUP) {
            Game.instance().getInvulnerable().add(event.getPlayer().getUniqueId());
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setHealth(20);
            event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " hat den Server betreten!");

            event.getPlayer().setGameMode(GameMode.CREATIVE);

            userSQL.deleteUser(event.getPlayer());
            userSQL.createUser(event.getPlayer());

            try {
                event.getPlayer().teleport(locationUtil.getLocation(event.getPlayer().getDisplayName()));
            } catch (Throwable throwable) {
                System.out.println("Die Location für den Spieler");
            }
        } else if (Game.instance().getCurrent() == GameState.INGAME) {
            event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.AQUA + " hat den Server betreten!");

            /*if (!Game.instance().getAlreadyJoined().contains(event.getPlayer().getUniqueId()) && !Game.instance().getInvulnerable().contains(event.getPlayer().getUniqueId())) {
                Game.instance().getInvulnerable().add(event.getPlayer().getUniqueId());
                Game.instance().getAlreadyJoined().add(event.getPlayer().getUniqueId());

                event.getPlayer().setGameMode(GameMode.ADVENTURE);

                BukkitScheduler countdown = Bukkit.getScheduler();

                CountdownLogin countdownLogin = new CountdownLogin(plugin, event.getPlayer());
                final int id = countdown.scheduleSyncRepeatingTask(plugin, countdownLogin, 0L, 20L);
                countdownLogin.setTaskID(id);
            }*/
        }
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 100L, 100L);
    }
}
