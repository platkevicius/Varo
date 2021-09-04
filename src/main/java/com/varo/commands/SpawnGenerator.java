package com.varo.commands;

import com.varo.models.Border;
import com.varo.models.ItemPool;
import com.varo.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnGenerator implements CommandExecutor {

    private final Border border;
    private LocationUtil locationUtil = new LocationUtil();
    private List<ItemStack> items = ItemPool.instance().getItemPoolSpawnChests();

    public SpawnGenerator(Border border) {
        this.border = border;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player commander = (Player) commandSender;

            if (commander.getName().equals("PlayNationDE") || commander.isOp()) {
                Location commanderLoc = commander.getLocation();
                //border.createBorder(commanderLoc, 100);

                int x = (int) Math.rint(commanderLoc.getX());
                int y = (int) Math.rint(commanderLoc.getY());
                int z = (int) Math.rint(commanderLoc.getZ());

                int playersAmount = Integer.parseInt(strings[0]);
                int distanceHoles = 10;
                double radiusHoles = (distanceHoles * playersAmount) / (2 * Math.PI);
                double arcHoles = 2 * Math.PI / playersAmount;
                double arcCounterHoles = 0;

                while (arcCounterHoles < 2 * Math.PI) {
                    double xSwitch = Math.sin(arcCounterHoles) * radiusHoles;
                    double zSwitch = Math.cos(arcCounterHoles) * radiusHoles;

                    Location current = new Location(commander.getWorld(), x + xSwitch, y, z + zSwitch);
                    setHole(current, commander);

                    arcCounterHoles += arcHoles;
                }

                int chestAmount = playersAmount / 2;
                int distanceChests = 4;
                double radiusChests = (distanceChests * chestAmount) / (2 * Math.PI);
                double arcChests = 2 * Math.PI / chestAmount;
                double arcCounterChests = 0;

                while (arcCounterChests < 2 * Math.PI) {
                    double xSwitch = Math.sin(arcCounterChests) * radiusChests;
                    double zSwitch = Math.cos(arcCounterChests) * radiusChests;

                    Location current = new Location(commander.getWorld(), x + xSwitch, y, z + zSwitch);
                    Location normedMinusOne = normLoc(current, commander);
                    Location normed = new Location(commander.getWorld(), normedMinusOne.getX(),normedMinusOne.getY() + 1, normedMinusOne.getZ());
                    Block block = normed.getBlock();
                    block.setType(Material.CHEST);
                    BlockData blockData = block.getBlockData();

                    if (blockData instanceof Directional) {
                        if (arcCounterChests <= Math.PI / 2)
                            ((Directional) blockData).setFacing(BlockFace.EAST);
                        else if (arcCounterChests <= Math.PI)
                            ((Directional) blockData).setFacing(BlockFace.NORTH);
                        else if (arcCounterChests <= 1.5 * Math.PI)
                            ((Directional) blockData).setFacing(BlockFace.WEST);
                        else
                            ((Directional) blockData).setFacing(BlockFace.SOUTH);

                        block.setBlockData(blockData);
                    }

                    if (normed.getBlock().getState() instanceof Chest) {
                        Chest chest = (Chest) normed.getBlock().getState();
                        Inventory inv = chest.getBlockInventory();

                        if (!items.isEmpty()) {
                            int randomAmountOfItems = new Random().nextInt(10);
                            List<Integer> generatedChestIndexes = new ArrayList<>();

                            while (randomAmountOfItems != 0) {
                                int chestIndex = new Random().nextInt(27);
                                while (generatedChestIndexes.contains(chestIndex)) {
                                    chestIndex = new Random().nextInt(27);
                                }

                                inv.setItem(chestIndex, items.remove(new Random().nextInt(items.size())));
                                generatedChestIndexes.add(chestIndex);
                                randomAmountOfItems--;
                            }
                        }
                    }

                    arcCounterChests += arcChests;
                }

                return true;
            }
            return false;
        }
        return false;
    }

    private Location normLoc(Location current, Player commander) {

        if (current.getBlock().getType() == Material.AIR) {
            Location dummy = current;
            while (dummy.getBlock().getType() == Material.AIR && dummy.getBlock().getType() != Material.GRASS) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y - 1, current.getZ());
            }

            return dummy;
        } else {
            Location dummy = current;
            while (dummy.getBlock().getType() != Material.AIR && dummy.getBlock().getType() != Material.GRASS) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y + 1, current.getZ());
            }

            return new Location(commander.getWorld(), current.getX(), dummy.getY() - 1, current.getZ());
        }
    }

    private void setHole(Location current, Player commander) {
        Location cur = normLoc(current, commander);

        //hole
        Location dummy;
        Location r;
        Location l;
        Location u;
        Location d;

        for (int c = 1; c <= 4; c++) {
            dummy = new Location(commander.getWorld(), cur.getX(), cur.getY() - c, cur.getZ());

            if (c <= 3) {
                if (c == 1)
                    dummy.getBlock().setType(Material.GRASS_BLOCK);
                else
                    dummy.getBlock().setType(Material.AIR);

                r = new Location(commander.getWorld(), dummy.getX() + 1, dummy.getY(), dummy.getZ());
                r.getBlock().setType(Material.STONE);

                l = new Location(commander.getWorld(), dummy.getX() - 1, dummy.getY(), dummy.getZ());
                l.getBlock().setType(Material.STONE);

                u = new Location(commander.getWorld(), dummy.getX(), dummy.getY(), dummy.getZ() + 1);
                u.getBlock().setType(Material.STONE);

                d = new Location(commander.getWorld(), dummy.getX(), dummy.getY(), dummy.getZ() - 1);
                d.getBlock().setType(Material.STONE);
            } else {
                dummy.getBlock().setType(Material.STONE);

            }
        }

        //slabs and upper hole
        Location no = new Location(commander.getWorld(), cur.getX() + 1, cur.getY() + 1, cur.getZ());
        no.getBlock().setType(Material.ACACIA_SLAB);
        Location noMinusOne = new Location(commander.getWorld(), no.getX(), no.getY() - 1, no.getZ());
        noMinusOne.getBlock().setType(Material.GRASS_BLOCK);

        Location ea = new Location(commander.getWorld(), cur.getX(), cur.getY() + 1, cur.getZ() + 1);
        ea.getBlock().setType(Material.ACACIA_SLAB);
        Location eaMinusOne = new Location(commander.getWorld(), ea.getX(), ea.getY() - 1, ea.getZ());
        eaMinusOne.getBlock().setType(Material.GRASS_BLOCK);

        Location so = new Location(commander.getWorld(), cur.getX() - 1, cur.getY() + 1, cur.getZ());
        so.getBlock().setType(Material.ACACIA_SLAB);
        Location soMinusOne = new Location(commander.getWorld(), so.getX(), so.getY() - 1, so.getZ());
        soMinusOne.getBlock().setType(Material.GRASS_BLOCK);

        Location we = new Location(commander.getWorld(), cur.getX(), cur.getY() + 1, cur.getZ() - 1);
        we.getBlock().setType(Material.ACACIA_SLAB);
        Location weMinusOne = new Location(commander.getWorld(), we.getX(), we.getY() - 1, we.getZ());
        weMinusOne.getBlock().setType(Material.GRASS_BLOCK);

        cur.getBlock().setType(Material.AIR);
    }
}
