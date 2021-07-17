package com.varo.listener;

import com.varo.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

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
}
