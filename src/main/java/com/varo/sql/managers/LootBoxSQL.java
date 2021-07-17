package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LootBoxSQL {

    private final MySQL mySQL;

    public LootBoxSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createNewLootBox(Location location) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO LootBox (x, y, z, opened) " +
                    "VALUES ('" + location.getX() + "'," +
                    "'" + location.getY() + "'," +
                    "'" + location.getZ() + "')," +
                    "'" + false + "'");

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isOpened(int id) {
        boolean opened = false;
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT opened FROM LootBox WHERE id = '" + id + "';");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            opened = resultSet.getBoolean(1);
            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return opened;
    }

    public void deleteLootBox() {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("DELETE FROM LootBox");
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setOpened(int id) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE LootBox SET opened = " + false + " WHERE " +
                    "id = '" + id + "'");
            preparedStatement.execute();

            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
