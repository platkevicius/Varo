package com.varo.commands;

import com.varo.Game;
import com.varo.GameState;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

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
            if (player.getName().equals("PlayNationDE")) {
                player.getWorld().setTime(6000);
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

                    int counter = 30;

                    @Override
                    public void run() {
                        if (counter == 0) {
                            Game.instance().getInvulnerable().clear();
                            Game.instance().setCurrent(GameState.INGAME);
                            Game.instance().setServerTime(1200);
                            chatUtil.sendAllPlayers(ChatColor.DARK_RED + "Mögen die Spiele beginnen!");
                            scheduler.cancelTasks(plugin);
                        } else if (counter % 5 == 0 || counter == 4 || counter == 3 || counter == 2 || counter == 1) {
                            chatUtil.sendAllPlayers(ChatColor.BLUE + "Varo " + ChatColor.GOLD + "beginnt in " + ChatColor.BLUE + counter + ChatColor.GOLD + " Sekunden");
                        }
                        counter--;
                    }
                }, 0L, 20L);
                return true;
            }
            return false;
        }
        return false;
    }
}
