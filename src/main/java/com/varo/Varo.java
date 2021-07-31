package com.varo;

import com.varo.commands.LootBoxCommand;
import com.varo.commands.Spawn;
import com.varo.commands.SpawnGenerator;
import com.varo.commands.Start;
import com.varo.listener.*;
import com.varo.manager.LootBoxManager;
import com.varo.models.Border;
import com.varo.sql.MySQL;
import com.varo.sql.SqlCredentials;
import com.varo.sql.managers.BorderSQL;
import com.varo.sql.managers.LootBoxSQL;
import com.varo.sql.managers.UserSQL;
import com.varo.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Varo extends JavaPlugin {

    private Config config;

    private MySQL mySQL;
    private SqlCredentials sqlCredentials;

    private BorderSQL borderSQL;
    private LootBoxSQL lootBoxSQL;
    private UserSQL userSQL;

    private Border border;
    private LootBoxManager lootBoxManager;

    @Override
    public void onDisable() {
        mySQL.closeConnection();
    }

    @Override
    public void onEnable() {
        config = new Config();
        sqlCredentials = new SqlCredentials("root", "", "localhost", "test");
        MySQL mySQL = new MySQL(sqlCredentials);

        System.out.println("Plugin Varo started");

        config.setUp();

        System.out.println("Database is connecting...");
        mySQL.connect();
        mySQL.setUp();
        System.out.println("Database has connected.");

        borderSQL = new BorderSQL(mySQL);
        lootBoxSQL = new LootBoxSQL(mySQL);
        userSQL = new UserSQL(mySQL);

        border = new Border(borderSQL);
        lootBoxManager = new LootBoxManager(lootBoxSQL);

        registerCommands();
        registerListener();
    }


    private void registerCommands() {
        Objects.requireNonNull(getCommand("start")).setExecutor(new Start(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn());
        Objects.requireNonNull(getCommand("generate")).setExecutor(new SpawnGenerator(border));
        Objects.requireNonNull(getCommand("lootbox")).setExecutor(new LootBoxCommand(lootBoxManager));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinEvent(this, userSQL), this);
        getServer().getPluginManager().registerEvents(new Basic(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new LoginEvent(userSQL), this);
        getServer().getPluginManager().registerEvents(new KillEvent(userSQL, border), this);
    }

}
