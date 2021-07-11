package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorderSQL {

    private final MySQL mySQL;

    public BorderSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createBorder(Location middle, int radius) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("INSERT INTO BorderCoordinates(x, y, z, radius) VALUES (" +
                                                                                         "'" + middle.getX() + "', '" + middle.getY() + "', '" + middle.getZ() + "'," +
                                                                                         "'" + radius + "')");

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRadius(int newRadius) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE BorderCoordinates SET radius = '" + newRadius + "'");

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
