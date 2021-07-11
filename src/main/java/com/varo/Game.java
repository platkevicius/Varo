package com.varo;

import com.varo.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static Game game;
    private final GameState warmup = GameState.WARMUP;
    private final GameState ingame = GameState.INGAME;
    private final GameState finish = GameState.FINISH;
    private GameState current;
    private boolean started = false;
    private final List<Player> invulnerable = new ArrayList<>();
    //private final HashMap<Player, Integer> playTime = new HashMap<>();
    private final HashMap<Player, Pair<Integer, BukkitScheduler>> serverTime = new HashMap<>();

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

    public HashMap<Player, Pair<Integer, BukkitScheduler>> getServerTime() {
        return serverTime;
    }

    public void setPlayTime(int i) {
            serverTime.replaceAll((k, v) -> new Pair<>(i, v.getE()));
    }

    public void resetPlayTime() {
        LocalTime midnight = LocalTime.parse("00:00");
        if (LocalTime.now().equals(midnight)) {
            serverTime.replaceAll((k, v) -> new Pair<>(60, v.getE()));
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
