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
        //TODO: Testen, ob nur event.getEntity() keinen Hunger verliert
        if (event.getEntity() instanceof Player && Game.instance().getInvulnerable().contains((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void healthChange(EntityDamageEvent event) {
        //TODO: Testen, ob nur event.getEntity() kein Leben verliert
        if (event.getEntity() instanceof Player && Game.instance().getInvulnerable().contains((Player)event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void dealingDamage(EntityDamageByEntityEvent event) {
        //TODO: Testen, ob nur event.getEntity() kein Leben verliert
        if (event.getDamager() instanceof Player && Game.instance().getInvulnerable().contains((Player)event.getDamager())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player && Game.instance().getInvulnerable().contains((Player)event.getTarget())) {
            event.setCancelled(true);
        }
    }
}
