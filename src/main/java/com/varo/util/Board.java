package com.varo.util;

import com.varo.sql.managers.TeamSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

    private final Scoreboard scoreboard;
    private final TeamSQL teamSQL;

    private final List<Team> teams;

    public Board(TeamSQL teamSQL) {
        this.teamSQL = teamSQL;
        teams = new ArrayList<>();

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = Objects.requireNonNull(scoreboardManager).getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("tablist", "dummy");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        registerTeams();
        updateScore();
    }

    public void registerTeams() {
        List<com.varo.models.Team> teams = teamSQL.getAllTeams();

        int counter = 0;
        for (com.varo.models.Team team : teams) {
            counter++;

            Team scoreTeam = scoreboard.registerNewTeam(team.getUser1() + "," + team.getUser2());
            scoreTeam.setPrefix("[Team " + counter + "]");
            scoreTeam.setAllowFriendlyFire(true);
            this.teams.add(scoreTeam);
        }
    }

    public void updateScore() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Team team : teams) {

            }
        }
    }

}
