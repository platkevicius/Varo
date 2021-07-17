package com.varo.sql.managers;

import com.varo.sql.MySQL;
import com.varo.sql.SqlCredentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class BorderSQLTest {

    private SqlCredentials sqlCredentials;
    private MySQL mySQL;
    private BorderSQL borderSQL;

    @Before
    public void setUp() {
        sqlCredentials = new SqlCredentials("root", "", "localhost", "test");
        mySQL = new MySQL(sqlCredentials);

        mySQL.connect();
        borderSQL = new BorderSQL(mySQL);
        mySQL.setUp();
    }

    @Test
    public void borderCreation() {
        Server server = Mockito.mock(Server.class);
        Location location = new Location(server.createWorld(WorldCreator.name("hallo")), 1, 1, 1);

        borderSQL.deleteBorder();
        borderSQL.createBorder(location, 5);
        assertEquals(5, borderSQL.getRadius());
    }

    @Test
    public void borderUpdate() {
        borderSQL.updateRadius(10);
        assertEquals(10, borderSQL.getRadius());
    }

}