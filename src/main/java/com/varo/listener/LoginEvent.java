package com.varo.listener;

import com.varo.Game;
import com.varo.sql.managers.UserSQL;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginEvent implements Listener {

    private final UserSQL userSQL;

    public LoginEvent(UserSQL userSQL) {
        this.userSQL = userSQL;
    }

    @EventHandler
    public void login(PlayerLoginEvent event) {
        /*if (Game.instance().getPlayTimeUsedUp().contains(event.getPlayer().getUniqueId()) ||
            !userSQL.isAlive(event.getPlayer())) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.DARK_RED + "Deine heutige Zeit auf dem Server ist aufgebraucht.");
        }
        else if (Game.instance().getBanned().contains(event.getPlayer().getUniqueId())) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.DARK_RED + "Aufgrund eines Regelverstoßes wurdest Du aus Varo ausgeschlossen.");
        }*/
    }
}
