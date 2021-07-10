package com.varo.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtil {

    private String prefix;
    private Config config;

    public ChatUtil() {
        config = new Config();
        prefix = config.getConfiguration().getString("prefix");
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(prefix + " " + message);
    }

    public void sendAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(prefix + " " + message);
    }

}
