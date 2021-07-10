package com.varo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Game {
    private static Game game;
    private final GameState warmup = GameState.WARMUP;
    private final GameState ingame = GameState.INGAME;
    private final GameState finish = GameState.FINISH;
    private GameState current;

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
}
