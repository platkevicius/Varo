package com.varo.runnables;

import com.varo.manager.LootBoxManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.LocalTime;

public class LootBoxSpawner implements Runnable {

    private final Plugin plugin;
    private final LootBoxManager lootBoxManager;

    public LootBoxSpawner(Plugin plugin, LootBoxManager lootBoxManager) {
        this.plugin = plugin;
        this.lootBoxManager = lootBoxManager;
    }

    @Override
    public void run() {
        if (lootBoxManager.hasNext()) {
            lootBoxManager.spawnLootBox(lootBoxManager.getLootBoxContent());

            //TOOD: run new tasklater
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.runTaskLater(plugin, new LootBoxSpawner(plugin, lootBoxManager), 100L);
        }
    }
}
