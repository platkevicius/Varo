package com.varo.manager;

import com.varo.models.LootBox;
import com.varo.sql.managers.LootBoxSQL;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootBoxManager {

    private final List<LootBox> lootBoxs;
    private final LootBoxSQL lootBoxSQL;

    public final long delayUntilNewSpawn = 200L;

    public LootBoxManager(LootBoxSQL lootBoxSQL) {
        this.lootBoxSQL = lootBoxSQL;
        lootBoxs = lootBoxSQL.getLotBoxes();
    }

    public void spawnLootBox(List<ItemStack> items) {
        if (!lootBoxs.isEmpty()) {
            LootBox lootBox = lootBoxs.remove(0);
            lootBoxSQL.setCreated(lootBox.getId());

            Location loc = lootBox.getLocation();
            loc.getBlock().setType(Material.CHEST);

            Block block = loc.getBlock();
            Chest chest = (Chest) block.getState();

            Inventory inv = chest.getInventory();
            chest.setCustomName("Lootbox: " + lootBox.getId());

            for (int i = 0; i < items.size(); i++) {
                inv.setItem(i, items.get(i));
            }

        }
    }

    public void createLootbox(Location location) {
        lootBoxSQL.createNewLootBox(location);
    }

    public boolean hasNext() {
        return !lootBoxs.isEmpty();
    }

    //TODO: Make this method dependent on the state of the game (players alive and time being played)
    public List<ItemStack> getLootBoxContent() {
        return List.of(new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND));
    }

}
