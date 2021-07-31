package com.varo.commands;

import com.varo.manager.LootBoxManager;
import com.varo.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LootBoxCommand implements CommandExecutor {

    private final LootBoxManager lootBoxManager;
    private final ChatUtil chatUtil = new ChatUtil();

    public LootBoxCommand(LootBoxManager lootBoxManager) {
        this.lootBoxManager = lootBoxManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("lootbox")) {
            lootBoxManager.createLootbox(player.getLocation());
            chatUtil.sendMessage(player, "Lootbox wurde erfolgreich gesetzt und in der Datenbank gespeichert");
        }

        return true;
    }
}
