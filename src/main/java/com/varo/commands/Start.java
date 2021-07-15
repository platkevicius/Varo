package com.varo.commands;

import com.varo.Game;
import com.varo.GameState;
import com.varo.runnables.CountdownLogin;
import com.varo.runnables.CountdownLogout;
import com.varo.runnables.CountdownStart;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class Start implements CommandExecutor {
    private final Plugin plugin;
    private final ChatUtil chatUtil = new ChatUtil();

    public Start(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.getName().equals("PlayNationDE") && Game.instance().getCurrent() == GameState.WARMUP && !Game.instance().isStarted()) {
                Game.instance().setStarted(true);
                player.getWorld().setTime(6000);

                BukkitScheduler scheduler = Bukkit.getScheduler();
                CountdownStart countdownStart = new CountdownStart(plugin);

                final int taskID = scheduler.scheduleSyncRepeatingTask(plugin, countdownStart, 0L, 20L);
                countdownStart.setTaskID(taskID);

                return true;
            }
            return false;
        }
        return false;
    }
}
