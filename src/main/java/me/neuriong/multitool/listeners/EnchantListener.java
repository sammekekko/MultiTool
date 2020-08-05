package me.neuriong.multitool.listeners;

import me.neuriong.multitool.MultiTool;
import me.neuriong.multitool.item.Item;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class EnchantListener implements Listener {

    private Item it = new Item();

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e) {
        if (it.compare(e.getItem().getItemMeta().getLore(), it.getLore())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnvil(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (it.compare(e.getCurrentItem().getItemMeta().getLore(), it.getLore())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
