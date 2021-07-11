package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.sql.PreparedStatement;
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

    public void addKill(Player killer, Player killed) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO UserKills (killer, killed) VALUES (?, ?)");

            preparedStatement.setString(1, killer.getUniqueId().toString());
            preparedStatement.setString(2, killer.getUniqueId().toString());

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
        return false;
    }

    public int getKills(Player player) {
        return 0;
    }

    public boolean isAlive() {
        return false;
    }

}
