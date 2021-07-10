package com.varo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private SqlCredentials sqlCredentials;

    private Connection connection;

    public MySQL(SqlCredentials sqlCredentials) {
        this.sqlCredentials = sqlCredentials;
    }

    public void setUp() {}

    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("");
            }
            catch (SQLException throwables) {
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

}
