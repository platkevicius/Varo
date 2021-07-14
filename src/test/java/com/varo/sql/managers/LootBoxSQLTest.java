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

public class LootBoxSQLTest {

    private SqlCredentials sqlCredentials;
    private MySQL mySQL;
    private LootBoxSQL lootBoxSQL;
    private Player player;
    private Server server;

    @Before
    public void setUp() {
        sqlCredentials = new SqlCredentials("root", "", "localhost", "test");
        mySQL = new MySQL(sqlCredentials);

        mySQL.connect();
        lootBoxSQL = new LootBoxSQL(mySQL);
        mySQL.setUp();

        server = Mockito.mock(Server.class);
        player = Mockito.mock(Player.class);

        Location location = new Location(server.createWorld(WorldCreator.name("test")), 0, 0, 0);

        Mockito.when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        Mockito.when(player.getLocation()).thenReturn(location);
    }

    @Test
    public void chestCreationTest() {
        lootBoxSQL.deleteLootBox();
        lootBoxSQL.createNewLootBox(player.getLocation());

        assertFalse(lootBoxSQL.isOpened(1));
    }

    @Test
    public void setChestStatusTest() {
        lootBoxSQL.deleteLootBox();
        lootBoxSQL.createNewLootBox(player.getLocation());

        lootBoxSQL.setOpened(1);
        assertTrue(lootBoxSQL.isOpened(1));
    }

}