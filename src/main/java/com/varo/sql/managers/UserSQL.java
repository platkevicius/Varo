package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserSQL {

    private final MySQL mySQL;

    public UserSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createUser(Player player) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO User (UUID, alive, lastLogging, online, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setBoolean(2, true);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now().toString()));
            preparedStatement.setBoolean(4, true);
            preparedStatement.setDouble(5, player.getLocation().getBlockX());
            preparedStatement.setDouble(6, player.getLocation().getBlockY());
            preparedStatement.setDouble(7, player.getLocation().getBlockZ());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(Player player) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("DELETE FROM User WHERE UUID = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addKill(Player killer, Player killed) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO UserKills (killer, killed) VALUES (?, ?)");

            preparedStatement.setString(1, killer.getUniqueId().toString());
            preparedStatement.setString(2, killed.getUniqueId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setLastLoging(Player player) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE User SET lastLogging = " + LocalDate.now().toString() + " WHERE UUID = ?;");

            preparedStatement.setString(1, player.getUniqueId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setOnline(Player player, boolean online) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE User SET online = " + online + " WHERE UUID = ?;");

            preparedStatement.setString(1, player.getUniqueId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isOnline(Player player) {
        boolean online = false;
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT online FROM User WHERE UUID = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                online = resultSet.getBoolean(1);
            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return online;
    }

    public int getKills(Player player) {
        int kills = 0;

        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT COUNT(id) AS killCount FROM UserKills WHERE killer = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                kills = resultSet.getInt("killCount");

            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return kills;
    }

    public boolean isAlive(Player player) {
        boolean alive = true;
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT alive FROM User WHERE UUID = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                alive = resultSet.getBoolean(1);

            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return alive;
    }

    public void dieUser(Player player) {
        try {
                PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE User SET alive = '0' WHERE UUID = ?;");

            preparedStatement.setString(1, player.getUniqueId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
