package com.varo.commands;

import com.varo.Game;
import com.varo.models.Border;
import com.varo.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnGenerator implements CommandExecutor {

    private final Border border;
    private LocationUtil locationUtil = new LocationUtil();
    private int counter;

    public SpawnGenerator(Border border) {
        this.border = border;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player commander = (Player) commandSender;

            if (commander.getName().equals("PlayNationDE")) {
                Location commanderLoc = commander.getLocation();
                //border.createBorder(commanderLoc, 100);

                int x = (int) Math.rint(commanderLoc.getX());
                int y = (int) Math.rint(commanderLoc.getY());
                int z = (int) Math.rint(commanderLoc.getZ());

                int playersAmount = Integer.parseInt(strings[0]);
                int distanceHoles = 10;
                double radius = (distanceHoles * playersAmount) / (2 * Math.PI);

                double arc = 2 * Math.PI / playersAmount;
                double arcCounter = 0;

                counter = 0;
                while (arcCounter < 2 * Math.PI) {
                    double xSwitch = Math.sin(arcCounter) * radius;
                    double zSwitch = Math.cos(arcCounter) * radius;

                    Location current = new Location(commander.getWorld(), x + xSwitch, y, z + zSwitch);
                    setHole(current, commander);

                    arcCounter += arc;
                }

                return true;
            }
            return false;
        }
        return false;
    }

    private Location normLoc(Location current, Player commander) {

        if (current.getBlock().getType() == Material.AIR || current.getBlock().getType() == Material.GRASS) {
            Location dummy = current;
            while (dummy.getBlock().getType() != Material.GRASS_BLOCK) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y - 1, current.getZ());
            }

            return new Location(commander.getWorld(), current.getX(), dummy.getY(), current.getZ());
        } else {
            Location dummy = current;
            while (dummy.getBlock().getType() != Material.GRASS_BLOCK) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y + 1, current.getZ());
            }

            return new Location(commander.getWorld(), current.getX(), dummy.getY(), current.getZ());
        }
    }

    private void setHole(Location current, Player commander) {
        Location cur = normLoc(current, commander);

        //hole with pistons and redstone mechanism
        cur.getBlock().setType(Material.AIR);
        for (double y = cur.getY() - 1; y >= cur.getY() - 5; y--) {
            Location loc = new Location(commander.getWorld(), cur.getX(), y, cur.getZ());
            if (y == cur.getY() - 1 || y == cur.getY() - 2) {
                loc.getBlock().setType(Material.AIR);
            } else if (y == cur.getY() - 3) {
                loc.getBlock().setType(Material.GRASS_BLOCK);
            } else if (y == cur.getY() - 4) {
                loc.getBlock().setType(Material.STICKY_PISTON);

                BlockData blockData = loc.getBlock().getBlockData();
                ((Directional) blockData).setFacing(BlockFace.UP);
                loc.getBlock().setBlockData(blockData);

                Location redstoneWire5 = new Location(commander.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
                Location below5 = new Location(commander.getWorld(), redstoneWire5.getX(), redstoneWire5.getY() - 1, redstoneWire5.getZ());
                Location upper5 = new Location(commander.getWorld(), redstoneWire5.getX(), redstoneWire5.getY() + 1, redstoneWire5.getZ());
                below5.getBlock().setType(Material.DIRT);
                upper5.getBlock().setType(Material.AIR);
                redstoneWire5.getBlock().setType(Material.REDSTONE_WIRE);

                Location redstoneWire6 = new Location(commander.getWorld(), loc.getX() + 1, loc.getY() + 1, loc.getZ() - 1);
                Location below6 = new Location(commander.getWorld(), redstoneWire6.getX(), redstoneWire6.getY() - 1, redstoneWire6.getZ());
                Location upper6 = new Location(commander.getWorld(), redstoneWire6.getX(), redstoneWire6.getY() + 1, redstoneWire6.getZ());
                below6.getBlock().setType(Material.DIRT);
                upper6.getBlock().setType(Material.AIR);
                redstoneWire6.getBlock().setType(Material.REDSTONE_WIRE);

                Location redstoneWire7 = new Location(commander.getWorld(), loc.getX(), loc.getY() + 2, loc.getZ() - 1);
                Location below7 = new Location(commander.getWorld(), redstoneWire7.getX(), redstoneWire7.getY() - 1, redstoneWire7.getZ());
                below7.getBlock().setType(Material.DIRT);
                redstoneWire7.getBlock().setType(Material.REDSTONE_WIRE);

            } else {
                loc.getBlock().setType(Material.STICKY_PISTON);

                BlockData blockData = loc.getBlock().getBlockData();
                ((Directional) blockData).setFacing(BlockFace.UP);
                loc.getBlock().setBlockData(blockData);

                Location torch = new Location(commander.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ() - 1);
                torch.getBlock().setType(Material.AIR);
                locationUtil.setLocation("torches.torch" + counter, torch);
                counter++;
                Location below = new Location(commander.getWorld(), torch.getX(), torch.getY() - 1, torch.getZ());
                below.getBlock().setType(Material.DIRT);

                Location redstoneWire1 = new Location(commander.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());
                Location below1 = new Location(commander.getWorld(), redstoneWire1.getX(), redstoneWire1.getY() - 1, redstoneWire1.getZ());
                below1.getBlock().setType(Material.DIRT);
                redstoneWire1.getBlock().setType(Material.REDSTONE_WIRE);

                Location redstoneWire2 = new Location(commander.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ() + 1);
                Location below2 = new Location(commander.getWorld(), redstoneWire2.getX(), redstoneWire2.getY() - 1, redstoneWire2.getZ());
                Location upper2 = new Location(commander.getWorld(), redstoneWire2.getX(), redstoneWire2.getY() + 1, redstoneWire2.getZ());
                below2.getBlock().setType(Material.DIRT);
                upper2.getBlock().setType(Material.AIR);
                redstoneWire2.getBlock().setType(Material.REDSTONE_WIRE);

                Location redstoneWire3 = new Location(commander.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ() + 1);
                Location below3 = new Location(commander.getWorld(), redstoneWire3.getX(), redstoneWire3.getY() - 1, redstoneWire3.getZ());
                below3.getBlock().setType(Material.DIRT);
                redstoneWire3.getBlock().setType(Material.REDSTONE_WIRE);

                Location redstoneWire4 = new Location(commander.getWorld(), loc.getX() + 1, loc.getY() + 1, loc.getZ() + 1);
                Location below4 = new Location(commander.getWorld(), redstoneWire4.getX(), redstoneWire4.getY() - 1, redstoneWire4.getZ());
                Location upper4 = new Location(commander.getWorld(), redstoneWire4.getX(), redstoneWire4.getY() + 1, redstoneWire4.getZ());
                upper4.getBlock().setType(Material.AIR);
                below4.getBlock().setType(Material.DIRT);
                redstoneWire4.getBlock().setType(Material.REDSTONE_WIRE);
            }
        }

        //slabs
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
    }
}
