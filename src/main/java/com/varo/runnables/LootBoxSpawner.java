package com.varo.runnables;

import com.varo.manager.LootBoxManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

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

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.runTaskLater(plugin, new LootBoxSpawner(plugin, lootBoxManager), lootBoxManager.delayUntilNewSpawn);
        }
    }
}
