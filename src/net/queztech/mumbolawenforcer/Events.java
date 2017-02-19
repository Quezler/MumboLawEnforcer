package net.queztech.mumbolawenforcer;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

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

    @EventHandler
    public void AFineLine(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            Integer airCount = 0;
            for (Block block : Helper.blocksAround(p.getLocation().add(0, -1, 0).getBlock(), 1)) {
                if (block.getType().equals(Material.AIR)) {
                    airCount++;
                }
            }
            if (airCount.equals(24)) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 4,0));
            }
        }
    }

    @EventHandler
    public void Aaaaahhhhhhh(BlockPhysicsEvent event) {
        Block b = event.getBlock();
        if (b.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (b.getType().equals(Material.GRAVEL)) {
                for (Player player : Helper.getPlayersInRange(5, b.getLocation()).keySet()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 1, 10));
                }
            }
        }
    }

    @EventHandler
    public void WetRedstone(BlockFromToEvent event) {;
        if (event.getBlock().getType().equals(Material.STATIONARY_WATER) ||
            event.getBlock().getType().equals(Material.WATER)) {
            for (Block block : Helper.blocksAround(event.getBlock(), 1)) {
                if (block.getType().toString().contains("REDSTONE")) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
