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
            }

            else dummy.getBlock().setType(Material.STONE);
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
