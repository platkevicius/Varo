package com.varo.listener;

import com.varo.models.Border;
import com.varo.sql.managers.UserSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillEvent implements Listener {

    private final UserSQL userSQL;
    private Border border;

    public KillEvent(UserSQL userSQL, Border border) {
        this.userSQL = userSQL;
        this.border = border;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player killed = event.getEntity().getPlayer();

        if (killer != null && killed != null)
            userSQL.addKill(killer, killed);

        if (killed != null) {
            userSQL.dieUser(killed);
            killed.kickPlayer("Du bist gestorben und somit aus Varo ausgeschieden!");

            border.decreseBorder();
        }
    }
}
