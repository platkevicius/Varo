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

    public LootBoxManager(LootBoxSQL lootBoxSQL) {
        this.lootBoxSQL = lootBoxSQL;
        lootBoxs = lootBoxSQL.getLotBoxes();
    }

    public void spawnLootBox(ItemStack[] items) {
        if (!lootBoxs.isEmpty()) {
            LootBox lootBox = lootBoxs.remove(0);
            lootBoxSQL.setCreated(lootBox.getId());

            Location loc = lootBox.getLocation();
            loc.getBlock().setType(Material.CHEST);

            Block block = loc.getBlock();
            Chest chest = (Chest) block.getState();

            Inventory inv = chest.getInventory();

            inv.setContents(items);
        }
    }

    public void createLootbox(Location location) {
        lootBoxSQL.createNewLootBox(location);
    }

}
