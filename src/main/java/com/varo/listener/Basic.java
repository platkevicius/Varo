package com.varo.listener;

import com.varo.Game;
import com.varo.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Basic implements Listener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (Game.instance().getCurrent() == GameState.WARMUP) {
            event.setCancelled(true);
        }
    }
}
