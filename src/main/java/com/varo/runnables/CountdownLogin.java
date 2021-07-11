package com.varo.runnables;

import com.varo.Game;
import com.varo.util.ChatUtil;
import com.varo.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CountdownLogin implements Runnable {
    private final Plugin plugin;
    private final Player player;
    private int counter = 15;
    private ChatUtil chatUtil = new ChatUtil();
    private int taskID;

    private BukkitScheduler ingameTime = Bukkit.getScheduler();

    public CountdownLogin(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run() {
        if (counter == 0) {
            chatUtil.sendAllPlayers(ChatColor.RED + player.getName() + ChatColor.GOLD + " ist jetzt angreifbar!");
            Game.instance().getInvulnerable().remove(player);
            player.getPlayer().setGameMode(GameMode.SURVIVAL);

            CountdownLogout countdownLogout = new CountdownLogout(plugin, player);
            final int idTime = ingameTime.scheduleSyncRepeatingTask(plugin, countdownLogout , 0L, 20L);
            Game.instance().getServerTime().put(player, new Pair<>(60, ingameTime));
            countdownLogout.setTaskID(idTime);

            plugin.getServer().getScheduler().cancelTask(taskID);
        } else if (counter % 5 == 0 || counter == 4 || counter == 3 || counter == 2 || counter == 1) {
            chatUtil.sendAllPlayers(ChatColor.RED + player.getName() + ChatColor.GOLD + " ist in " + ChatColor.RED + counter + ChatColor.GOLD + " Sekunden angreifbar!");
        }
        counter--;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
