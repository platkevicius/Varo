package com.varo;

import com.varo.models.Border;
import com.varo.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import java.time.LocalTime;
import java.util.*;

public class Game {
    private static Game game;
    private final GameState warmup = GameState.WARMUP;
    private final GameState ingame = GameState.INGAME;
    private final GameState finish = GameState.FINISH;
    private GameState current;

    private boolean started = false;

    private final List<UUID> invulnerable = new ArrayList<>();
    private final List<UUID> playTimeUsedUp = new ArrayList<>();
    private final List<UUID> alreadyJoined = new ArrayList<>();
    private final List<Location> redstoneTorches = new ArrayList<>();

    private Border border;

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

    public List<UUID> getInvulnerable() {
        return invulnerable;
    }

    public void resetPlayTime() {
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<UUID> getPlayTimeUsedUp() {
        return playTimeUsedUp;
    }

    public List<UUID> getAlreadyJoined() {
        return alreadyJoined;
    }

    public List<Location> getRedstoneTorches() {
        return redstoneTorches;
    }
}
