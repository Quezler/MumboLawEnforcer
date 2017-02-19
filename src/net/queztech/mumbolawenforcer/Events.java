package net.queztech.mumbolawenforcer;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

class Events implements Listener {

    private MLE plugin;

    Events(MLE mle) {
        this.plugin = mle;
    }

    @EventHandler
    public void didStraightDown(BlockBreakEvent event) {
        if (event.getPlayer().getLocation().add(0, -1, 0).getBlock().equals(event.getBlock())) { // is targeting block below player
            if (event.getBlock().getType().equals(Material.STONE)) {
                if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void FilthyRedstone(EntityChangeBlockEvent event) {
        if (event.getEntity().getType().equals(EntityType.ENDERMAN)) {
            for (Block block : Helper.blocksAround(event.getBlock(), 1)) {
                if (block.getType().toString().contains("REDSTONE")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
