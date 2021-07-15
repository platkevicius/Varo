package com.varo.runnables;

import com.varo.Game;
import com.varo.GameState;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class CountdownStart implements Runnable {
    private final Plugin plugin;
    private ChatUtil chatUtil = new ChatUtil();
    private int taskID;
    private int counter = 5;

    public CountdownStart(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void run() {
        if (counter == 0) {
            Game.instance().getInvulnerable().clear();
            Game.instance().setCurrent(GameState.INGAME);
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                BukkitScheduler ingameTime = Bukkit.getScheduler();
                CountdownLogout logout = new CountdownLogout(plugin, player1);

                Game.instance().getAlreadyJoined().add(player1.getUniqueId());
                final int taskID = ingameTime.scheduleSyncRepeatingTask(plugin, logout, 0L, 20L);
                logout.setTaskID(taskID);
            }
            chatUtil.sendAllPlayers(ChatColor.DARK_RED + "Mögen die Spiele beginnen!");

            plugin.getServer().getScheduler().cancelTask(taskID);
        } else if (counter % 5 == 0 || counter == 4 || counter == 3 || counter == 2 || counter == 1) {
            chatUtil.sendAllPlayers(ChatColor.BLUE + "Varo " + ChatColor.GOLD + "beginnt in " + ChatColor.BLUE + counter + ChatColor.GOLD + " Sekunden");
        }
        counter--;
    }
}
