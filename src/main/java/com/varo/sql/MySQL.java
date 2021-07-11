package com.varo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final SqlCredentials sqlCredentials;

    private Connection connection;

    public MySQL(SqlCredentials sqlCredentials) {
        this.sqlCredentials = sqlCredentials;
    }

    public void setUp() {
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS User (UUID VARCHAR(64) PRIMARY KEY," +
                                                 "alive BOOLEAN, lastLogging DATE, online BOOLEAN, x DOUBLE, y DOUBLE, z DOUBLE);");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS UserKills (id INT PRIMARY KEY, killer VARCHAR(64), killed VARCHAR(64));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS LootBox(id INT PRIMARY KEY AUTO_INCREMENT, x DOUBLE, y DOUBLE, z DOUBLE, opened BOOLEAN)");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS BorderCoordinates(id INT PRIMARY KEY AUTO_INCREMENT, x DOUBLE, y DOUBLE, z DOUBLE, radius INT)");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void connect() {
        if (!isConnected()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + sqlCredentials.getHost() +
                                                         ":" + sqlCredentials.getPort() + "/" + sqlCredentials.getDb(), sqlCredentials.getUsername(), sqlCredentials.getPassword());
            }
            catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connection.close();
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
