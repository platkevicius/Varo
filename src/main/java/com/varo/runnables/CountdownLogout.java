package com.varo.runnables;

import com.varo.Game;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class CountdownLogout implements Runnable {
    private final Plugin plugin;
    private final Player player;
    private ChatUtil chatUtil = new ChatUtil();
    private int taskID;
    private int counter;
    private boolean enemyNearby = false;

    public CountdownLogout(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.counter = 1200;
    }

    @Override
    public void run() {
        if (counter == 0) {

            if (!player.isOnline()) {
                Game.instance().getAlreadyJoined().remove(player.getUniqueId());
                Game.instance().getPlayTimeUsedUp().add(player.getUniqueId());

                plugin.getServer().getScheduler().cancelTask(taskID);
                return;
            }

            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (otherPlayer.getLocation().distance(player.getLocation()) <= 50)
                    enemyNearby = true;
            }

            if (!enemyNearby) {
                Game.instance().getAlreadyJoined().remove(player.getUniqueId());
                Game.instance().getPlayTimeUsedUp().add(player.getUniqueId());
                chatUtil.sendAllPlayers(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " wurde vom Server gekickt.");
                Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).kickPlayer(ChatColor.DARK_RED + "Deine heutige Zeit auf dem Server ist aufgebraucht." + ChatColor.RED + " Du wurdest deshalb gekickt.");
                plugin.getServer().getScheduler().cancelTask(taskID);

            } else {
                chatUtil.sendMessage(player, ChatColor.DARK_RED + "Du bist zu nah an einem Gegner!");
            }


        } else if ((counter == 15 || counter == 10 || counter == 5 || counter == 4 || counter == 3 || counter == 2 || counter == 1) && player.isOnline())
            chatUtil.sendAllPlayers(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " wird in " + ChatColor.YELLOW + counter + ChatColor.AQUA + " Sekunden vom Server gekickt.");
        counter--;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}