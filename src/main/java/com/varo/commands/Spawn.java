package com.varo.commands;

import com.varo.Game;
import com.varo.util.ChatUtil;
import com.varo.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    private final LocationUtil locationUtil = new LocationUtil();
    private final ChatUtil chatUtil = new ChatUtil();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        if (!commandSender.getName().equalsIgnoreCase("PlayNationDE") && !commandSender.isOp())
            return true;

        Player player = (Player) commandSender;

        if (strings.length == 1) {
            String playerName = strings[0];

            locationUtil.setLocation(playerName, player.getLocation());
            chatUtil.sendMessage(player, "Du hast erfolgreich den Spawn für den Spieler: " + playerName + " gesetzt!");
        }
        return true;
    }

}
