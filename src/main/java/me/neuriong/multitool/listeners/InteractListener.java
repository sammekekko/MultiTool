package me.neuriong.multitool.listeners;

import me.neuriong.multitool.item.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class InteractListener implements Listener {

    private HashMap<Material, Material> hash = new HashMap<>();

    private Item h = new Item();

    private void registerItems() {
        hash.put(Material.ENDER_CHEST, Material.DIAMOND_PICKAXE);
        hash.put(Material.ANVIL, Material.DIAMOND_PICKAXE);
        hash.put(Material.COAL_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.REDSTONE_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.ENCHANTMENT_TABLE, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_FENCE, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_DOOR, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_TRAPDOOR, Material.DIAMOND_PICKAXE);
        hash.put(Material.MOB_SPAWNER, Material.DIAMOND_PICKAXE);
        hash.put(Material.DISPENSER, Material.DIAMOND_PICKAXE);
        hash.put(Material.DROPPER, Material.DIAMOND_PICKAXE);
        hash.put(Material.FURNACE, Material.DIAMOND_PICKAXE);
        hash.put(Material.COAL_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.ENDER_STONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.HOPPER, Material.DIAMOND_PICKAXE);
        hash.put(Material.QUARTZ_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.BRICK_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.BRICK, Material.DIAMOND_PICKAXE);
        hash.put(Material.CAULDRON, Material.DIAMOND_PICKAXE);
        hash.put(Material.COBBLESTONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.COBBLE_WALL, Material.DIAMOND_PICKAXE);
        hash.put(Material.COBBLESTONE_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.MOSSY_COBBLESTONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.NETHER_BRICK, Material.DIAMOND_PICKAXE);
        hash.put(Material.NETHER_BRICK_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.NETHER_FENCE, Material.DIAMOND_PICKAXE);
        hash.put(Material.STONE_SLAB2, Material.DIAMOND_PICKAXE);
        hash.put(Material.DOUBLE_STONE_SLAB2, Material.DIAMOND_PICKAXE);
        hash.put(Material.STONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.SMOOTH_BRICK, Material.DIAMOND_PICKAXE);
        hash.put(Material.SMOOTH_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.QUARTZ_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.QUARTZ_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.RED_SANDSTONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.RED_SANDSTONE_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.SANDSTONE, Material.DIAMOND_PICKAXE);
        hash.put(Material.SANDSTONE_STAIRS, Material.DIAMOND_PICKAXE);
        hash.put(Material.BREWING_STAND, Material.DIAMOND_PICKAXE);
        hash.put(Material.STONE_PLATE, Material.DIAMOND_PICKAXE);
        hash.put(Material.GOLD_PLATE, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_PLATE, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.GLOWING_REDSTONE_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.NETHERRACK, Material.DIAMOND_PICKAXE);
        hash.put(Material.IRON_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.LAPIS_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.LAPIS_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.DIAMOND_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.EMERALD_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.GOLD_BLOCK, Material.DIAMOND_PICKAXE);
        hash.put(Material.DIAMOND_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.EMERALD_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.GOLD_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.REDSTONE_ORE, Material.DIAMOND_PICKAXE);
        hash.put(Material.OBSIDIAN, Material.DIAMOND_PICKAXE);
        hash.put(Material.ICE, Material.DIAMOND_PICKAXE);
        hash.put(Material.PACKED_ICE, Material.DIAMOND_PICKAXE);
        hash.put(Material.RAILS, Material.DIAMOND_PICKAXE);
        hash.put(Material.ACTIVATOR_RAIL, Material.DIAMOND_PICKAXE);
        hash.put(Material.DETECTOR_RAIL, Material.DIAMOND_PICKAXE);
        hash.put(Material.POWERED_RAIL, Material.DIAMOND_PICKAXE);
        hash.put(Material.STAINED_CLAY, Material.DIAMOND_PICKAXE);
        hash.put(Material.HARD_CLAY, Material.DIAMOND_PICKAXE);
        hash.put(Material.DOUBLE_STEP, Material.DIAMOND_PICKAXE);
        hash.put(Material.STEP, Material.DIAMOND_PICKAXE);

        hash.put(Material.CLAY, Material.DIAMOND_SPADE);
        hash.put(Material.GRASS, Material.DIAMOND_SPADE);
        hash.put(Material.GRAVEL, Material.DIAMOND_SPADE);
        hash.put(Material.MYCEL, Material.DIAMOND_SPADE);
        hash.put(Material.SAND, Material.DIAMOND_SPADE);
        hash.put(Material.SOUL_SAND, Material.DIAMOND_SPADE);
        hash.put(Material.DIRT, Material.DIAMOND_SPADE);
        hash.put(Material.SOIL, Material.DIAMOND_SPADE);

        hash.put(Material.TRAP_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.DARK_OAK_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.ACACIA_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.BIRCH_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.JUNGLE_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.SPRUCE_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.WOOD_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.WOODEN_DOOR, Material.DIAMOND_AXE);
        hash.put(Material.CHEST, Material.DIAMOND_AXE);
        hash.put(Material.TRAPPED_CHEST, Material.DIAMOND_AXE);
        hash.put(Material.WORKBENCH, Material.DIAMOND_AXE);
        hash.put(Material.FENCE, Material.DIAMOND_AXE);
        hash.put(Material.SPRUCE_FENCE, Material.DIAMOND_AXE);
        hash.put(Material.JUNGLE_FENCE, Material.DIAMOND_AXE);
        hash.put(Material.DARK_OAK_FENCE, Material.DIAMOND_AXE);
        hash.put(Material.ACACIA_FENCE, Material.DIAMOND_AXE);
        hash.put(Material.BIRCH_FENCE, Material.DIAMOND_AXE);
        hash.put(Material.ACACIA_FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.BIRCH_FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.DARK_OAK_FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.JUNGLE_FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.SPRUCE_FENCE_GATE, Material.DIAMOND_AXE);
        hash.put(Material.JUKEBOX, Material.DIAMOND_AXE);
        hash.put(Material.LOG, Material.DIAMOND_AXE);
        hash.put(Material.LOG_2, Material.DIAMOND_AXE);
        hash.put(Material.WOOD_STEP, Material.DIAMOND_AXE);
        hash.put(Material.WOOD_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.BOOKSHELF, Material.DIAMOND_AXE);
        hash.put(Material.BANNER, Material.DIAMOND_AXE);
        hash.put(Material.JACK_O_LANTERN, Material.DIAMOND_AXE);
        hash.put(Material.MELON_BLOCK, Material.DIAMOND_AXE);
        hash.put(Material.PUMPKIN, Material.DIAMOND_AXE);
        hash.put(Material.SIGN, Material.DIAMOND_AXE);
        hash.put(Material.SIGN_POST, Material.DIAMOND_AXE);
        hash.put(Material.WALL_SIGN, Material.DIAMOND_AXE);
        hash.put(Material.NOTE_BLOCK, Material.DIAMOND_AXE);
        hash.put(Material.WOOD_PLATE, Material.DIAMOND_AXE);
        hash.put(Material.LADDER, Material.DIAMOND_AXE);
        hash.put(Material.COCOA, Material.DIAMOND_AXE);
        hash.put(Material.DAYLIGHT_DETECTOR, Material.DIAMOND_AXE);
        hash.put(Material.DAYLIGHT_DETECTOR_INVERTED, Material.DIAMOND_AXE);
        hash.put(Material.HUGE_MUSHROOM_1, Material.DIAMOND_AXE);
        hash.put(Material.HUGE_MUSHROOM_2, Material.DIAMOND_AXE);
        hash.put(Material.VINE, Material.DIAMOND_AXE);
        hash.put(Material.WOOD, Material.DIAMOND_AXE);
        hash.put(Material.SPRUCE_WOOD_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.DARK_OAK_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.BIRCH_WOOD_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.ACACIA_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.JUNGLE_WOOD_STAIRS, Material.DIAMOND_AXE);
        hash.put(Material.WOOD_DOUBLE_STEP, Material.DIAMOND_AXE);

        hash.put(Material.CROPS, Material.DIAMOND_HOE);
        hash.put(Material.CARROT, Material.DIAMOND_HOE);
        hash.put(Material.POTATO, Material.DIAMOND_HOE);
        hash.put(Material.NETHER_WARTS, Material.DIAMOND_HOE);
        hash.put(Material.SUGAR_CANE_BLOCK, Material.DIAMOND_HOE);
    }

    public InteractListener() {
        registerItems();
    }

    @EventHandler
    public void onDamage(BlockDamageEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
            if (h.compare(p.getItemInHand().getItemMeta().getLore(), h.getLore())) {
                if (block != null && block.getType() != Material.AIR && block.getType() != null) {
                    if (hash.get(block.getType()) != null) {
                        p.getItemInHand().setType(hash.get(block.getType()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType() != Material.DIAMOND_HOE) {
                if (h.compare(p.getItemInHand().getItemMeta().getLore(), h.getLore())) {
                    if (block.getType() == Material.GRASS || block.getType() == Material.DIRT) {
                        if (block.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                            Material mat = Material.DIAMOND_HOE;
                            p.getItemInHand().setType(mat);
                            p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() + 1));
                            block.setType(Material.SOIL);
                        }
                    }
                }
            }
        }
    }
}
