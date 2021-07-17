package com.varo.listener;

import com.varo.Game;
import com.varo.sql.managers.UserSQL;
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
        System.out.println(userSQL.isAlive(event.getPlayer()));
        System.out.println(event.getPlayer().getUniqueId().toString());
        if (Game.instance().getPlayTimeUsedUp().contains(event.getPlayer().getUniqueId()) ||
            !userSQL.isAlive(event.getPlayer())) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, "Zeit verbraucht");
        }
    }
}
