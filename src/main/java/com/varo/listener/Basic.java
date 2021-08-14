package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import com.varo.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Basic implements Listener {

    @EventHandler
    public void foodChange(FoodLevelChangeEvent event) {
        if ((event.getEntity() instanceof Player) && Game.instance().getInvulnerable().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void healthChange(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && Game.instance().getInvulnerable().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void dealingDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && Game.instance().getInvulnerable().contains(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player && Game.instance().getInvulnerable().contains(event.getTarget().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerQuits(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        int ping = event.getPlayer().getPing();

        if (ping >= 400) {
            Game.instance().getBanned().add(player.getUniqueId());
            player.kickPlayer("Aufgrund eines Regelverstoßes wurdest Du aus dem Projekt ausgeschlossen.");
        }
    }

    @EventHandler
    public void createPortal(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof Player && Game.instance().equals(GameState.INGAME)) {
            event.setCancelled(true);
            Player crafter = (Player) event.getEntity();
            crafter.sendMessage("Es dürfen keine weiteren Portale hergestellt werden!");
        }
    }
}
