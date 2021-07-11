package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.Location;

import java.sql.PreparedStatement;
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

}
