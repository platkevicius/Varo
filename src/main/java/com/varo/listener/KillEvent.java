package com.varo.listener;

import com.varo.models.Border;
import com.varo.sql.managers.UserSQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;

public class KillEvent implements Listener {

    private final UserSQL userSQL;
    private final Border border;

    public KillEvent(UserSQL userSQL, Border border) {
        this.userSQL = userSQL;
        this.border = border;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player killed = event.getEntity().getPlayer();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 5, 1);
        }

        if (killer != null && killed != null)
            userSQL.addKill(killer, killed);

        if (killed != null) {
            userSQL.dieUser(killed);
            killed.kickPlayer("Du bist gestorben und somit aus Varo ausgeschieden!");

            border.decreseBorder();

            //TODO: to be tested
            Location loc = killed.getLocation();

            loc.getBlock().setType(Material.CHEST);
            Chest chest = (Chest) loc.getBlock().getState();

            Inventory inv = chest.getInventory();
            inv.addItem(killed.getInventory().getContents());
            inv.addItem(killed.getInventory().getArmorContents());
        }
    }
}
