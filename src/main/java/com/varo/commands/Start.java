package com.varo.commands;

import com.varo.Game;
import com.varo.GameState;
import com.varo.manager.LootBoxManager;
import com.varo.runnables.CountdownStart;
import com.varo.runnables.LootBoxSpawner;
import com.varo.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Start implements CommandExecutor {
    private final Plugin plugin;
    private final LocationUtil locationUtil = new LocationUtil();

    private final LootBoxManager lootBoxManager;

    public Start(Plugin plugin, LootBoxManager lootBoxManager) {
        this.plugin = plugin;
        this.lootBoxManager = lootBoxManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.getName().equals("PlayNationDE") && Game.instance().getCurrent() == GameState.WARMUP && !Game.instance().isStarted() || player.isOp()) {
                Game.instance().setStarted(true);
                player.getWorld().setTime(6000);

                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    Location loc = player1.getLocation();
                    player1.teleport(new Location(player1.getWorld(), loc.getX(), loc.getY() + 3, loc.getZ()));
                }

                BukkitScheduler scheduler = Bukkit.getScheduler();
                CountdownStart countdownStart = new CountdownStart(plugin);

                final int taskID = scheduler.scheduleSyncRepeatingTask(plugin, countdownStart, 0L, 20L);
                countdownStart.setTaskID(taskID);

                BukkitScheduler lootBoxScheduler = Bukkit.getScheduler();
                LootBoxSpawner lootBoxSpawner = new LootBoxSpawner(plugin, lootBoxManager);

                lootBoxScheduler.runTaskLater(plugin, lootBoxSpawner, lootBoxManager.delayUntilNewSpawn);

                return true;
            }
            return false;
        }
        return false;
    }
}
