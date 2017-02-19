package net.queztech.mumbolawenforcer;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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

    @EventHandler
    public void KillingOffTheNiceGuys(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        if (victim.getType().equals(EntityType.VILLAGER)) {
            if (Helper.getPlayersInRange(64, victim.getLocation()).size() > 0) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void HotHands(BlockIgniteEvent event) {
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            Player p = event.getPlayer();

            if (!p.isSneaking()) {
                event.setCancelled(true);
            }
       }
    }

    @EventHandler
    // commented code is to assist debugging
    public void ThePillarLeaver(ChunkLoadEvent event) {
//    public void ThePillarLeaver(EntityToggleGlideEvent event) {

        Chunk c = event.getChunk();
//        Chunk c = event.getEntity().getLocation().getChunk();

        // https://bukkit.org/threads/get-all-blocks-in-a-chunk.311213/

        if (!event.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            return;
        }

//        System.out.print(c.toString());

        new BukkitRunnable() {
            @Override
            public void run() {
                for(int xx = 1; xx < 15; xx++) {
                    for(int zz = 1; zz < 15; zz++) {
                        Block block = c.getWorld().getHighestBlockAt(c.getBlock(xx, 0, zz).getLocation());

                        Integer airCount = 0;
                        for (Block around : Helper.blocksAround(block, 1)) {
                            if (around.getType().equals(Material.AIR)) {
                                airCount++;
                            }
                        }

                        if (airCount == 26) {
                            block.getLocation().add(0, -1, 0).getBlock().breakNaturally();
                        }
                    }
                }
            }
        }.runTask(plugin);

    }

    @EventHandler
    public void MonsterBreaker(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.MOB_SPAWNER)) {
            if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EnderSmack(EntityTargetEvent event){
        if (event.getTarget() instanceof Player) {
            Player p = (Player) event.getTarget();

            ItemStack heldItem = (p.getInventory().getItem(p.getInventory().getHeldItemSlot()));
            if (heldItem != null && heldItem.getType().equals(Material.ENDER_PEARL)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void WrongSwing(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player p = (Player) event.getDamager();

            ItemStack heldItem = (p.getInventory().getItem(p.getInventory().getHeldItemSlot()));
            if (heldItem != null && heldItem.getType().toString().contains("PICKAXE")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void MassMurderer(EntityDamageEvent event) {
        Entity here = event.getEntity();

        if (here instanceof Animals) {

            Integer nearbyFromSameBreed = 0;
            for (Entity there : here.getNearbyEntities(16, 16, 16)) {
                if (here.getType().equals(there.getType())) {
                    nearbyFromSameBreed++;
                }
            }

            if (nearbyFromSameBreed <= 1) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ToolSmasher(PlayerItemBreakEvent event) {
        Player p = event.getPlayer();
        p.setHealth(p.getHealth() - 10);
        p.getWorld().dropItemNaturally(p.getLocation(), event.getBrokenItem());
    }

    @EventHandler
    public void GoneFishing(PlayerFishEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void ScaryBossMan(CreatureSpawnEvent event) {
        if (event.getEntity().getType().equals(EntityType.WITHER)) {
            if (event.getLocation().getY() <= 50) {

            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void DiamondOnHoes(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType().equals(Material.DIAMOND_HOE)) {
            event.setCancelled(true);
        }
    }

}
