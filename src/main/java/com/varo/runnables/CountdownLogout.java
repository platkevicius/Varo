package com.varo.runnables;

import com.varo.Game;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
            Game.instance().getAlreadyJoined().remove(player.getUniqueId());
            Game.instance().getPlayTimeUsedUp().add(player.getUniqueId());

            if (player.isOnline())
                Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).kickPlayer("Deine heutige Zeit auf dem Server ist aufgebraucht!");

            plugin.getServer().getScheduler().cancelTask(taskID);
        }
        else if ((counter == 15 || counter == 10 || counter == 5 || counter == 4 || counter == 3 || counter == 2 || counter == 1) && player.isOnline())
            chatUtil.sendAllPlayers(ChatColor.RED + player.getName() + ChatColor.GOLD + " wird in " + ChatColor.RED + counter + ChatColor.GOLD + " Sekunden vom Server gekickt!");
        counter--;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}