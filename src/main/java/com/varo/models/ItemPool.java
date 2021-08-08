package com.varo.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPool {
    public static ItemPool itemPool;

    public static ItemPool instance() {
        if (itemPool == null) {
            itemPool = new ItemPool();
        }
        return itemPool;
    }

    public List<ItemStack> getItemPoolSpawnChests() {
        List<ItemStack> items = new ArrayList();

        items.add(new ItemStack(Material.WOODEN_SWORD,1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.ACACIA_WOOD, 16));
        items.add(new ItemStack(Material.DIRT,1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT,1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT, 16));
        items.add(new ItemStack(Material.BIRCH_PLANKS,1));
        items.add(new ItemStack(Material.BIRCH_BOAT, 1));
        items.add(new ItemStack(Material.BIRCH_BOAT, 1));
        items.add(new ItemStack(Material.PORKCHOP, 1));
        items.add(new ItemStack(Material.CHICKEN, 16));
        items.add(new ItemStack(Material.CHICKEN_SPAWN_EGG,1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.WOODEN_AXE, 1));
        items.add(new ItemStack(Material.COBWEB, 16));
        items.add(new ItemStack(Material.FLINT_AND_STEEL,1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT, 1));
        items.add(new ItemStack(Material.DIRT,1));
        items.add(new ItemStack(Material.WOODEN_HOE, 1));
        items.add(new ItemStack(Material.WOODEN_HOE, 1));
        items.add(new ItemStack(Material.WOODEN_HOE, 1));
        items.add(new ItemStack(Material.WOODEN_SHOVEL, 1));
        items.add(new ItemStack(Material.WOODEN_SHOVEL,1));
        items.add(new ItemStack(Material.WOODEN_SHOVEL, 1));
        items.add(new ItemStack(Material.WOODEN_PICKAXE, 1));
        items.add(new ItemStack(Material.WOODEN_PICKAXE, 1));
        items.add(new ItemStack(Material.WOODEN_PICKAXE, 1));
        items.add(new ItemStack(Material.STICK,1));
        items.add(new ItemStack(Material.FLINT, 1));
        items.add(new ItemStack(Material.APPLE, 1));
        items.add(new ItemStack(Material.APPLE, 1));
        items.add(new ItemStack(Material.APPLE, 8));
        items.add(new ItemStack(Material.RABBIT_FOOT, 8));

        return items;
    }
}
