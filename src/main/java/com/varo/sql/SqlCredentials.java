package com.varo.sql;

public class SqlCredentials {

    private String username;
    private String password;
    private String host;
    private String db;
    private int port;

    public SqlCredentials(String username, String password, String host, String db) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.db = db;
        this.port = 3306;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDb() {
        return db;
    }

    public int getPort() {
        return port;
    }
}
