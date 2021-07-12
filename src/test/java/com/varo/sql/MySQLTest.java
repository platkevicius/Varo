package com.varo.sql;

import org.junit.Before;
import org.junit.Test;

public class MySQLTest {

    private SqlCredentials sqlCredentials;
    private MySQL mySQL;

    @Before
    public void setUp() {
        sqlCredentials = new SqlCredentials("root", "", "localhost", "test");
        mySQL = new MySQL(sqlCredentials);

        mySQL.connect();
    }

    @Test
    public void defaultTables() {
        mySQL.setUp();
    }


}