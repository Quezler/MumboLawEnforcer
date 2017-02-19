package net.queztech.mumbolawenforcer;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    // https://www.spigotmc.org/threads/tutorial-getting-blocks-in-a-cube-radius.64981/
    public static ArrayList<Block> blocksAround(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    // https://www.spigotmc.org/threads/nearby-players.66861/
    public static Map<Player, Double> getPlayersInRange(int range, Location origin) {
        Map<Player, Double> back = new HashMap<>();
        origin.getWorld().getPlayers().forEach((p) -> {
            double d = p.getLocation().distanceSquared(origin);
            if (d <= range) {
                back.put(p, d);
            }
        });
        return back;
    }
}
