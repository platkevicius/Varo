package com.varo;

import com.varo.commands.Start;
import com.varo.listener.Basic;
import com.varo.listener.JoinEvent;
import com.varo.listener.LeaveEvent;
import com.varo.sql.MySQL;
import com.varo.sql.SqlCredentials;
import com.varo.sql.managers.BorderSQL;
import com.varo.sql.managers.LootBoxSQL;
import com.varo.sql.managers.UserSQL;
import com.varo.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Varo extends JavaPlugin {

    private Config config;

    private MySQL mySQL;
    private SqlCredentials sqlCredentials;

    private BorderSQL borderSQL;
    private LootBoxSQL lootBoxSQL;
    private UserSQL userSQL;

    @Override
    public void onDisable() {
        mySQL.closeConnection();
    }

    @Override
    public void onEnable() {
        config = new Config();
        sqlCredentials = new SqlCredentials("", "", "", "");
        MySQL mySQL = new MySQL(sqlCredentials);

        System.out.println("Plugin Varo started");

        config.setUp();

        System.out.println("Database is connecting...");
        mySQL.connect();
        System.out.println("Database has connected.");

        borderSQL = new BorderSQL(mySQL);
        lootBoxSQL = new LootBoxSQL(mySQL);
        userSQL = new UserSQL(mySQL);

        registerCommands();
        registerListener();
    }


    private void registerCommands() {
        getCommand("start").setExecutor(new Start(this));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new Basic(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
    }

}
