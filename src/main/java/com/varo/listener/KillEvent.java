package com.varo.listener;

import com.varo.sql.managers.UserSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillEvent implements Listener {

    private final UserSQL userSQL;

    public KillEvent(UserSQL userSQL) {
        this.userSQL = userSQL;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player killed = event.getEntity().getPlayer();

        if (killer != null && killed != null)
            userSQL.addKill(killer, killed);
    }

}
