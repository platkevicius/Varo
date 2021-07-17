package com.varo.sql.managers;

import com.varo.sql.MySQL;
import com.varo.sql.SqlCredentials;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserSQLTest {

    private SqlCredentials sqlCredentials;
    private MySQL mySQL;
    private UserSQL userSQL;
    private Player player;
    private Server server;

    @Before
    public void setUp() {
        sqlCredentials = new SqlCredentials("root", "", "localhost", "test");
        mySQL = new MySQL(sqlCredentials);

        mySQL.connect();
        userSQL = new UserSQL(mySQL);
        mySQL.setUp();

        server = Mockito.mock(Server.class);
        player = Mockito.mock(Player.class);

        Location location = new Location(server.createWorld(WorldCreator.name("test")), 0, 0, 0);

        Mockito.when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        Mockito.when(player.getLocation()).thenReturn(location);
    }

    @Test
    public void createUser() {
        userSQL.deleteUser(player);
        userSQL.createUser(player);

        assertTrue(userSQL.isAlive(player));
        assertTrue(userSQL.isOnline(player));
        assertEquals(0, userSQL.getKills(player));
    }

    @Test
    public void setOnlineTest() {
        userSQL.deleteUser(player);
        userSQL.createUser(player);

        userSQL.setOnline(player, false);
        assertFalse(userSQL.isOnline(player));

        userSQL.setOnline(player, true);
        assertTrue(userSQL.isOnline(player));
    }

    @Test
    public void killTest() {
        userSQL.deleteUser(player);
        userSQL.createUser(player);

        Player killed = Mockito.mock(Player.class);
        Location location = new Location(server.createWorld(WorldCreator.name("test")), 0, 0, 0);

        Mockito.when(killed.getUniqueId()).thenReturn(UUID.randomUUID());
        Mockito.when(killed.getLocation()).thenReturn(location);

        userSQL.deleteUser(killed);
        userSQL.createUser(killed);

        assertEquals(0, userSQL.getKills(player));
        assertEquals(0, userSQL.getKills(killed));

        userSQL.addKill(player, killed);
        assertEquals(1, userSQL.getKills(player));

        userSQL.addKill(killed, player);
        assertEquals(1, userSQL.getKills(killed));
    }

}