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
                border.createBorder(commanderLoc, 100);

                int x = (int) Math.rint(commanderLoc.getX());
                int z = (int) Math.rint(commanderLoc.getZ());

                int playersAmount = Integer.parseInt(strings[0]);
                int distanceHoles = 8;
                int radius = (int) Math.rint(distanceHoles * playersAmount / 2 * Math.PI);

                Location cur = new Location(commander.getWorld(), 0, 0, 0);

                setHole(commander.getWorld(), cur, commander);
                //commander.getLocation().getBlock().getType() == Material.AIR;
                return true;
            }
            return false;
        }
        return false;
    }

    private void setHole(World world, Location current, Player commander) {

        Location cur = current;

        if (cur.getBlock().getType() == Material.AIR) {

            while (cur.getBlock().getType() == Material.AIR) {
                cur = new Location(commander.getWorld(), cur.getX(), cur.getY() - 1, cur.getZ());
            }

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

        else {
            Location curPlusOne = new Location(commander.getWorld(), cur.getX(), cur.getY() + 1, cur.getZ());

            while (curPlusOne.getBlock().getType() != Material.AIR) {
                curPlusOne = new Location(commander.getWorld(), curPlusOne.getX(), curPlusOne.getY() + 1, curPlusOne.getZ());
            }

        }
    }
}
