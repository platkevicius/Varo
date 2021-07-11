package com.varo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    private static Game game;
    private final GameState warmup = GameState.WARMUP;
    private final GameState ingame = GameState.INGAME;
    private final GameState finish = GameState.FINISH;
    private GameState current;
    private final List<Player> invulnerable = new ArrayList<>();
    private final HashMap<Player, Integer> serverTime = new HashMap<>();

    private Game() {
        setCurrent(warmup);
    }

    public static Game instance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public GameState getCurrent() {
        return current;
    }

    public void setCurrent(GameState current) {
        this.current = current;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(current.getGameMode());
        }
    }

    public List<Player> getInvulnerable() {
        return invulnerable;
    }

    public HashMap<Player, Integer> getServerTime() {
        return serverTime;
    }

    public void setServerTime(int i) {
        LocalTime midnight = LocalTime.parse("00:00");
        if (LocalTime.now().equals(midnight)) {
            for (var player : serverTime.entrySet()) {
                player.setValue(i);
            }
        }
    }
}
