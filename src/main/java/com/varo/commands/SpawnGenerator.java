package com.varo.commands;

import com.varo.models.Border;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnGenerator implements CommandExecutor {

    private final Border border;

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
                int radius = (int) Math.rint((distanceHoles * playersAmount) / (2 * Math.PI));

                Location right = new Location(commander.getWorld(), x + radius, y, z);
                setHole(right, commander);

                Location left = new Location(commander.getWorld(), x - radius, y, z);
                setHole(left, commander);

                if (playersAmount % 4 == 0) {
                    Location up = new Location(commander.getWorld(), x, y, z + radius);
                    setHole(up, commander);

                    Location down = new Location(commander.getWorld(), x, y, z - radius);
                    setHole(down, commander);
                }

                double arc = 2 * Math.PI / playersAmount;
                double arcCounter = arc;

                while (arcCounter < Math.PI / 2) {
                    int xSwitch = (int) Math.rint(Math.cos(arcCounter)) * radius;
                    int zSwitch = (int) Math.rint(Math.sin(arcCounter)) * radius;

                    Location upperRight = new Location(commander.getWorld(), x + xSwitch, y, z + zSwitch);
                    setHole(upperRight, commander);

                    /*Location upperLeft = new Location(commander.getWorld(), x - xSwitch, y, Math.rint(z + zSwitch));
                    setHole(upperLeft, commander);

                    Location lowerRight = new Location(commander.getWorld(), x + xSwitch, y, z - zSwitch);
                    setHole(lowerRight, commander);

                    Location lowerLeft = new Location(commander.getWorld(), x - xSwitch, y, z - zSwitch);
                    setHole(lowerLeft, commander);

                     */

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
            while (dummy.getBlock().getType() == Material.AIR) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y - 1, current.getZ());
            }
            Location cur = new Location(commander.getWorld(), current.getX(), dummy.getY(), current.getZ());
            return cur;

        } else {
            Location dummy = current;
            while (dummy.getBlock().getType() != Material.AIR) {
                int y = (int) Math.rint(dummy.getY());
                dummy = new Location(commander.getWorld(), current.getX(), y + 1, current.getZ());
            }
            Location cur = new Location(commander.getWorld(), current.getX(), dummy.getY() - 1, current.getZ());
            return cur;
        }
    }

    private void setHole(Location current, Player commander) {
        Location cur = normLoc(current, commander);

        cur.getBlock().setType(Material.AIR);

        Location no = new Location(commander.getWorld(), cur.getX() + 1, cur.getY() + 1, cur.getZ());
        no.getBlock().setType(Material.ACACIA_SLAB);

        Location ea = new Location(commander.getWorld(), cur.getX(), cur.getY() + 1, cur.getZ() + 1);
        ea.getBlock().setType(Material.ACACIA_SLAB);

        Location so = new Location(commander.getWorld(), cur.getX() - 1, cur.getY() + 1, cur.getZ());
        so.getBlock().setType(Material.ACACIA_SLAB);

        Location we = new Location(commander.getWorld(), cur.getX(), cur.getY() + 1, cur.getZ() - 1);
        we.getBlock().setType(Material.ACACIA_SLAB);
    }
}
