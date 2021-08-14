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

    public CountdownLogout(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.counter = 1200;
    }

    @Override
    public void run() {
        if (counter == 0) {
            boolean enemyNearby = false;
            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();

            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (Math.sqrt(Math.pow(otherPlayer.getLocation().getX() - x, 2) + Math.pow(otherPlayer.getLocation().getY() - y, 2) + Math.pow(otherPlayer.getLocation().getZ() - z, 2)) <= 50) {
                    enemyNearby = true;
                }
            }

            if (!player.isOnline()) {
                Game.instance().getAlreadyJoined().remove(player.getUniqueId());
                Game.instance().getPlayTimeUsedUp().add(player.getUniqueId());
                plugin.getServer().getScheduler().cancelTask(taskID);
            }

            else {
                if (!enemyNearby) {
                    Game.instance().getAlreadyJoined().remove(player.getUniqueId());
                    Game.instance().getPlayTimeUsedUp().add(player.getUniqueId());
                    Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).kickPlayer("Deine heutige Zeit auf dem Server ist aufgebraucht!");
                    plugin.getServer().getScheduler().cancelTask(taskID);
                }
                else {
                    chatUtil.sendMessage(player, "Du bist zu nah an einem Gegner");
                }
            }
        }

        else if ((counter == 15 || counter == 10 || counter == 5 || counter == 4 || counter == 3 || counter == 2 || counter == 1) && player.isOnline())
            chatUtil.sendAllPlayers(ChatColor.RED + player.getName() + ChatColor.GOLD + " wird in " + ChatColor.RED + counter + ChatColor.GOLD + " Sekunden vom Server gekickt!");
        counter--;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}