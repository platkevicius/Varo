package com.varo.sql.managers;

import com.varo.sql.MySQL;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorderSQL {

    private final MySQL mySQL;

    public BorderSQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createBorder(Location middle, double radius) {
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

    public double getRadius() {
        double value = 0D;
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("SELECT radius FROM BorderCoordinates");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                value = resultSet.getDouble(1);

            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }

    public void updateRadius(double newRadius) {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("UPDATE BorderCoordinates SET radius = '" + newRadius + "'");

            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteBorder() {
        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("DELETE FROM BorderCoordinates");
            preparedStatement.execute();

            preparedStatement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
