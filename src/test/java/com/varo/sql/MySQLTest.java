package com.varo.sql;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySQLTest {

    private SqlCredentials sqlCredentials;
    private MySQL mySQL;

    @Before
    public void setUp() {
        sqlCredentials = new SqlCredentials("", "", "", "");
        mySQL = new MySQL(sqlCredentials);
    }

    @Test
    public void testConnection() {
        mySQL.connect();
    }



}