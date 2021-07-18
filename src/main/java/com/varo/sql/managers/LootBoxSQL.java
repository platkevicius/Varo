package com.varo.sql.managers;

import com.varo.models.LootBox;
import com.varo.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LootBoxSQL {

    private final MySQL mySQL;

    public LootBoxSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createNewLootBox(Location location) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO LootBox (world, x, y, z, opened, created) " +
                    "VALUES ('" + location.getWorld().getUID() + "'," +
                    "'" + location.getX() + "'," +
                    "'" + location.getY() + "'," +
                    "'" + location.getZ() + "')," +
                    "'" + false + "', " +
                    "'" + false + "'");

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<LootBox> getLotBoxes() {
        List<LootBox> lootBoxes = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT * FROM LootBox WHERE created='" + false + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String world = resultSet.getString(2);
                double x = resultSet.getDouble(3);
                double y = resultSet.getDouble(4);
                double z = resultSet.getDouble(5);
                boolean opened = resultSet.getBoolean(6);

                LootBox lootBox = new LootBox(id, new Location(Bukkit.getWorld(world), x, y, z), opened);
                lootBoxes.add(lootBox);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lootBoxes;
    }

    public boolean isOpened(int id) {
        boolean opened = false;
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT opened FROM LootBox WHERE id = '" + id + "';");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
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
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE LootBox SET opened = " + true + " WHERE " +
                    "id = '" + id + "'");
            preparedStatement.execute();

            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setCreated(int id) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE LootBox SET created = " + true + " WHERE " +
                    "id = '" + id + "'");
            preparedStatement.execute();

            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
