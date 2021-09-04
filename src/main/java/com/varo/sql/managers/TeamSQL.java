package com.varo.sql.managers;

import com.varo.models.Team;
import com.varo.sql.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamSQL {

    private final MySQL mySQL;
    private final Map<String, String> teams;

    public TeamSQL(MySQL mySQL) {
        this.mySQL = mySQL;
        teams = new HashMap<>();

        loadTeams();
    }

    public void createTeam(Player player1, Player player2) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO Teams(player1, player2) VALUES (?, ?)");

            preparedStatement.setString(1, player1.getUniqueId().toString());
            preparedStatement.setString(2, player2.getUniqueId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadTeams() {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT player1, player2 FROM Teams");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teams.put(resultSet.getString(1), resultSet.getString(2));
                teams.put(resultSet.getString(2), resultSet.getString(1));
            }

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isTeammateOfPlayer(Player player1, Player player2) {
        return teams.get(player1.getUniqueId().toString()).equals(player2.getUniqueId().toString()) ||
                teams.get(player2.getUniqueId().toString()).equals(player1.getUniqueId().toString());
    }

    public List<Team> getAllTeams() {
        List<Team> allTeams = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT * FROM TEAMS");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String user1 = resultSet.getString("playser1");
                String user2 = resultSet.getString("playser1");

                Team team = new Team(user1, user2);
                allTeams.add(team);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allTeams;
    }
}