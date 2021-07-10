package com.varo;

import org.bukkit.GameMode;

public enum GameState {
    WARMUP(GameMode.ADVENTURE), INGAME(GameMode.SURVIVAL), FINISH(GameMode.ADVENTURE);

    private GameMode gameMode;

    GameState(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
