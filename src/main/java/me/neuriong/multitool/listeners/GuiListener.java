package me.neuriong.multitool.listeners;

import me.neuriong.multitool.MultiTool;
import me.neuriong.multitool.commands.multitool;
import me.neuriong.multitool.enchantments.Enchantments;
import me.neuriong.multitool.item.Item;
import me.neuriong.multitool.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class GuiListener implements Listener {

    private MultiTool main;

    private multitool mt;

    private Enchantments enchs;

    private Item it = new Item();

    private Economy economy = MultiTool.economy;

    public GuiListener(MultiTool main) {
        this.main = main;
        mt = new multitool(main);
        enchs = new Enchantments();
    }

    public String convertEnchantment(String name, int level) {
        StringBuilder convertedLevel = new StringBuilder();
        for (int i = 0; i < level; i++) {
            convertedLevel.append("I");
        }
        if (enchs.hash.get(Enchantment.getByName(name)) != null) {
            name = enchs.hash.get(Enchantment.getByName(name));
        }

        return ChatColor.AQUA + name + " " + ChatColor.GRAY + convertedLevel.toString().replace("IIIII", "V").replace("IIII", "IV");
    }

    private void barrier(ItemStack itemStack, String name) {
        final String displayName = itemStack.getItemMeta().getDisplayName();
        final List<String> lore = itemStack.getItemMeta().getLore();
        itemStack.setType(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(null);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        Bukkit.getScheduler().runTaskLater(MultiTool.main, () -> {
            itemStack.setType(Material.ENCHANTED_BOOK);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }, 30L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(Utils.chat(main.getConfig().getString("gui.title")))) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                e.setCancelled(true);
                ItemStack itemInHand = p.getItemInHand();
                ItemStack item = e.getCurrentItem();
                final String maxBarrierName = Utils.chat(main.getConfig().getString("max_level_name"));
                final String noMoneyName = Utils.chat(main.getConfig().getString("no_money_name"));
                final String alreadyRepaired = Utils.chat(main.getConfig().getString("already_repaired_name"));
                if (e.getClick() == ClickType.LEFT) {
                    if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.fortune.name")))) {
                            int fortune = 1;
                            int cost = main.getConfig().getInt("gui.fortune.cost");
                            if (itemInHand.getItemMeta().getEnchants().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                                fortune = itemInHand.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1;
                            }
                            if (fortune > Enchantment.LOOT_BONUS_BLOCKS.getMaxLevel()) {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, maxBarrierName);
                                return;
                            }
                            if (economy.getBalance(p) >= cost * fortune * fortune) {
                                if (!economy.withdrawPlayer(p, cost * fortune * fortune).transactionSuccess()) {
                                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                    barrier(item, noMoneyName);
                                    return;
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, noMoneyName);
                                return;
                            }
                            p.getItemInHand().addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, fortune);
                            ItemMeta meta = itemInHand.getItemMeta();
                            List<String> newLore = it.getLore();
                            meta.setLore(newLore);
                            for (Enchantment ench : meta.getEnchants().keySet()) {
                                newLore.add(convertEnchantment(ench.getName(), p.getItemInHand().getEnchantmentLevel(ench)));
                            }
                            meta.setLore(newLore);
                            itemInHand.setItemMeta(meta);
                            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 2F);
                            mt.createUpgradeMenu(p);
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.efficiency.name")))) {
                            int efficiency = 1;
                            int cost = main.getConfig().getInt("gui.efficiency.cost");
                            if (itemInHand.getItemMeta().getEnchants().containsKey(Enchantment.DIG_SPEED)) {
                                efficiency = itemInHand.getItemMeta().getEnchantLevel(Enchantment.DIG_SPEED) + 1;
                            }
                            if (efficiency > Enchantment.DIG_SPEED.getMaxLevel()) {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, maxBarrierName);
                                return;
                            }
                            if (economy.getBalance(p) >= cost * efficiency * efficiency) { //cost + 300 * Math.pow(efficiency, cost / 7)
                                if (!economy.withdrawPlayer(p, cost * efficiency * efficiency).transactionSuccess()) {
                                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                    barrier(item, noMoneyName);
                                    return;
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, noMoneyName);
                                return;
                            }
                            p.getItemInHand().addUnsafeEnchantment(Enchantment.DIG_SPEED, efficiency);

                            ItemMeta meta = itemInHand.getItemMeta();
                            List<String> newLore = it.getLore();
                            meta.setLore(newLore);
                            for (Enchantment ench : meta.getEnchants().keySet()) {
                                newLore.add(convertEnchantment(ench.getName(), p.getItemInHand().getEnchantmentLevel(ench)));
                            }
                            meta.setLore(newLore);
                            itemInHand.setItemMeta(meta);
                            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 2F);
                            mt.createUpgradeMenu(p);
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.unbreaking.name")))) {
                            int unbreaking = 1;
                            int cost = main.getConfig().getInt("gui.unbreaking.cost");
                            if (itemInHand.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY)) {
                                unbreaking = itemInHand.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1;
                            }
                            if (unbreaking > Enchantment.DURABILITY.getMaxLevel()) {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, maxBarrierName);
                                return;
                            }
                            if (economy.getBalance(p) >= cost * unbreaking * unbreaking) {
                                if (!economy.withdrawPlayer(p, cost * unbreaking * unbreaking).transactionSuccess()) {
                                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                    barrier(item, noMoneyName);
                                    return;
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, noMoneyName);
                                return;
                            }
                            p.getItemInHand().addUnsafeEnchantment(Enchantment.DURABILITY, unbreaking);

                            ItemMeta meta = itemInHand.getItemMeta();
                            List<String> newLore = it.getLore();
                            meta.setLore(newLore);
                            for (Enchantment ench : meta.getEnchants().keySet()) {
                                newLore.add(convertEnchantment(ench.getName(), p.getItemInHand().getEnchantmentLevel(ench)));
                            }
                            meta.setLore(newLore);
                            itemInHand.setItemMeta(meta);
                            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 2F);
                            mt.createUpgradeMenu(p);
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.haste.name")))) {
                            int haste = 1;
                            int cost = main.getConfig().getInt("gui.haste.cost");
                            if (itemInHand.getItemMeta().getEnchants().containsKey(Enchantment.getByName("Haste"))) {
                                haste = itemInHand.getItemMeta().getEnchantLevel(Enchantment.getByName("Haste")) + 1;
                            }
                            if (haste > Enchantment.getByName("Haste").getMaxLevel()) {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, maxBarrierName);
                                return;
                            }
                            if (economy.getBalance(p) >= cost * haste * haste) {
                                if (!economy.withdrawPlayer(p, cost * haste * haste).transactionSuccess()) {
                                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                    barrier(item, noMoneyName);
                                    return;
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, noMoneyName);
                                return;
                            }
                            p.getItemInHand().addUnsafeEnchantment(Enchantment.getByName("Haste"), haste);

                            ItemMeta meta = itemInHand.getItemMeta();
                            List<String> newLore = it.getLore();
                            meta.setLore(newLore);
                            for (Enchantment ench : meta.getEnchants().keySet()) {
                                newLore.add(convertEnchantment(ench.getName(), p.getItemInHand().getEnchantmentLevel(ench)));
                            }
                            meta.setLore(newLore);
                            itemInHand.setItemMeta(meta);
                            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 2F);
                            p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                            final PotionEffect hasteEffect = PotionEffectType.FAST_DIGGING.createEffect(999999999, haste - 1);
                            p.addPotionEffect(hasteEffect);

                            mt.createUpgradeMenu(p);
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.repair.name")))) {
                            int cost = main.getConfig().getInt("gui.repair.cost");
                            if (p.getItemInHand().getDurability() == (short) (0)) {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, alreadyRepaired);
                                return;
                            }
                            if (economy.getBalance(p) >= cost) {
                                if (!economy.withdrawPlayer(p, cost).transactionSuccess()) {
                                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                    barrier(item, noMoneyName);
                                    return;
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                                barrier(item, noMoneyName);
                                return;
                            }
                            itemInHand.setDurability((short) 0);
                            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 2F);
                            if (main.getConfig().getBoolean("close_inventory_on_fix")) {
                                p.closeInventory();
                            } else {
                                mt.createUpgradeMenu(p);
                            }
                        }
                    } else if (e.getCurrentItem().getType() == Material.BARRIER) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat(main.getConfig().getString("gui.exit.name")))) {
                            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5F, 2F);
                            p.closeInventory();
                        }
                    }
                }
            }
        }
    }
}

