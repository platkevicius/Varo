package com.varo.runnables;

import com.varo.Game;
import com.varo.util.ChatUtil;
import com.varo.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CountdownLogout implements Runnable{
    private final Plugin plugin;
    private final Player player;
    private ChatUtil chatUtil = new ChatUtil();
    private int taskID;
    private int counter;

    public CountdownLogout(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.counter = 10;
    }

    @Override
    public void run() {
        if (counter == 0) {
            player.kickPlayer("Deine heutige Zeit auf dem Server ist aufgebraucht!");
            plugin.getServer().getScheduler().cancelTask(taskID);
        }
        else if (counter % 5 == 0 || counter == 4 || counter == 3 || counter == 2 || counter == 1)
            chatUtil.sendAllPlayers(ChatColor.RED + player.getName() + ChatColor.GOLD + " wird in " + ChatColor.RED + counter + ChatColor.GOLD + " Sekunden vom Server gekickt!");
        counter--;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}